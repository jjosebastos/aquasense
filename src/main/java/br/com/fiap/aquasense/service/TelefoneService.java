package br.com.fiap.aquasense.service;

import br.com.fiap.aquasense.dto.request.TelefoneRequest;
import br.com.fiap.aquasense.dto.response.TelefoneResponse;
import br.com.fiap.aquasense.model.Telefone;
import br.com.fiap.aquasense.repository.TelefoneRepository;
import br.com.fiap.aquasense.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

@Service
public class TelefoneService {

    @Autowired
    private TelefoneRepository telefoneRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Transactional
    public TelefoneResponse create(TelefoneRequest request){
        var userFound = usuarioRepository.findById(request.getIdUsuario())
                .orElseThrow();
        var telefone = new Telefone();
        telefone.setDdd(request.getDdd());
        telefone.setNumero(request.getNumero());
        telefone.setTipo(request.getTipo());
        telefone.setUsuario(userFound);
        var telefoneSaved = telefoneRepository.save(telefone);
        return toTelefoneResponse(telefoneSaved);
    }


    @Transactional
    public TelefoneResponse update(Long id, TelefoneRequest request){
        var telefone = telefoneRepository.findById(id)
                .orElseThrow();
        var userFound = usuarioRepository.findById(id)
                .orElseThrow();

        telefone.setDdd(request.getDdd());
        telefone.setNumero(request.getNumero());
        telefone.setTipo(request.getTipo());
        telefone.setDataAtualizacao(LocalDateTime.now());
        telefone.setUsuario(userFound);
        var telefoneSaved = telefoneRepository.save(telefone);
        return toTelefoneResponse(telefoneSaved);
    }

    @Transactional
    public void delete(Long id){
        var telefone = telefoneRepository.findById(id)
                .orElseThrow();
        telefoneRepository.delete(telefone);
    }

    public TelefoneResponse findById(Long id){
        var telefone = telefoneRepository.findById(id)
                .orElseThrow();
        return toTelefoneResponse(telefone);
    }

    public List<TelefoneResponse> findAll(){
        var telefone = telefoneRepository.findAll();
        if(telefone.isEmpty()){
            throw new NoSuchElementException();
        }
        return telefone.stream()
                .map(this::toTelefoneResponse)
                .toList();
    }

    private TelefoneResponse toTelefoneResponse(Telefone telefone){
        return TelefoneResponse.builder()
                .idTelefone(telefone.getIdTelefone())
                .ddd(telefone.getDdd())
                .numero(telefone.getNumero())
                .tipo(telefone.getTipo())
                .idUsuario(telefone.getUsuario().getIdUsuario())
                .build();
    }
}
