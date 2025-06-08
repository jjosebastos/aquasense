package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.LocalizacaoRequest;
import br.com.fiap.aquasense.dto.response.LocalizacaoResponse;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.model.auth.Usuario; // <<<< NOVO IMPORT: Para poder fazer o cast do principal
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication; // <<<< NOVO IMPORT
import org.springframework.security.core.context.SecurityContextHolder; // <<<< NOVO IMPORT
import org.springframework.security.core.userdetails.UsernameNotFoundException; // <<<< NOVO IMPORT para a exceção
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class LocalizacaoService {
    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    // Método auxiliar para obter o Usuario logado
    private Usuario getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !authentication.isAuthenticated()) {
            throw new SecurityException("Usuário não autenticado.");
        }

        if (authentication.getPrincipal() instanceof Usuario) {
            return (Usuario) authentication.getPrincipal();
        } else {
            String username = authentication.getName(); 
            return usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("Usuário logado não encontrado no sistema."));
        }
    }


    @Transactional
    public LocalizacaoResponse create(LocalizacaoRequest request) {
        var usuarioLogado = getAuthenticatedUser(); //PEGA O USUÁRIO LOGADO

        var localizacao = new Localizacao();
        localizacao.setNome(request.getNome());
        localizacao.setLatitude(request.getLatitude());
        localizacao.setLongitude(request.getLongitude());
        localizacao.setDataUltimaAtualizacao(LocalDateTime.now());
        localizacao.setStatus(request.getStatus());
        localizacao.setUsuario(usuarioLogado);

        var localizacaoSaved = this.localizacaoRepository.save(localizacao);
        return toLocalizacaoResponse(localizacaoSaved);
    }

    @Transactional
    public LocalizacaoResponse update(Long id, LocalizacaoRequest request) {
        var localizacaoFound = this.localizacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Localização não encontrada com o ID: " + id));
        
        var usuarioLogado = getAuthenticatedUser();
        if (!localizacaoFound.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            throw new SecurityException("Usuário não tem permissão para atualizar esta localização.");
        }

        localizacaoFound.setNome(request.getNome());
        localizacaoFound.setLatitude(request.getLatitude());
        localizacaoFound.setLongitude(request.getLongitude());
        localizacaoFound.setDataUltimaAtualizacao(LocalDateTime.now());
        localizacaoFound.setStatus(request.getStatus());

        var localizacaoSaved = this.localizacaoRepository.save(localizacaoFound);
        return toLocalizacaoResponse(localizacaoSaved);
    }

    @Transactional
    public void delete(Long id) {
        var localizacaoFound = this.localizacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Localização não encontrada para exclusão com o ID: " + id));

        var usuarioLogado = getAuthenticatedUser();
        if (!localizacaoFound.getUsuario().getIdUsuario().equals(usuarioLogado.getIdUsuario())) {
            throw new SecurityException("Usuário não tem permissão para excluir esta localização.");
        }

        this.localizacaoRepository.deleteById(id);
    }

    public LocalizacaoResponse findById(Long id) {
        var localizacao = this.localizacaoRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Localização não encontrada com o ID: " + id));
        

        return toLocalizacaoResponse(localizacao);
    }

    public List<LocalizacaoResponse> findAll() {

        var localizacao = this.localizacaoRepository.findAll();
        if (localizacao.isEmpty()) {
            throw new NoSuchElementException("Nenhuma localização encontrada.");
        }
        return localizacao.stream()
                .map(this::toLocalizacaoResponse)
                .toList();
    }

    private LocalizacaoResponse toLocalizacaoResponse(Localizacao localizacao) {
        return new LocalizacaoResponse(
                localizacao.getIdLocalizacao(),
                localizacao.getNome(),
                localizacao.getLatitude(),
                localizacao.getLongitude(),
                localizacao.getDataUltimaAtualizacao(),
                localizacao.getStatus(),
                localizacao.getUsuario().getIdUsuario());
    }
}