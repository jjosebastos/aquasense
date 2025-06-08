package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.LocalizacaoRequest;
import br.com.fiap.aquasense.dto.response.LocalizacaoResponse;
import br.com.fiap.aquasense.model.Localizacao;
import br.com.fiap.aquasense.repository.LocalizacaoRepository;
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class LocalizacaoService {
    @Autowired
    private LocalizacaoRepository localizacaoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public LocalizacaoResponse create(LocalizacaoRequest request) {
        var userFound = this.usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow();
        var localizacao = new Localizacao();
        localizacao.setNome(request.getNome());
        localizacao.setLatitude(request.getLatitude());
        localizacao.setLongitude(request.getLongitude());
        localizacao.setDataUltimaAtualizacao(request.getDataUltimaAtualizacao());
        localizacao.setStatus(request.getStatus());
        localizacao.setUsuario(userFound);
        var localizacaoSaved = this.localizacaoRepository.save(localizacao);
        return toLocalizacaoResponse(localizacaoSaved);
    }



    public LocalizacaoResponse findById(@PathVariable Long id) {
        var localizacao = this.localizacaoRepository.findById(id)
                .orElseThrow();
        return toLocalizacaoResponse(localizacao);
    }

    public List<LocalizacaoResponse> findAll() {
        var localizacao = this.localizacaoRepository.findAll();
        if (localizacao.isEmpty()) {
            throw new NoSuchElementException();
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
