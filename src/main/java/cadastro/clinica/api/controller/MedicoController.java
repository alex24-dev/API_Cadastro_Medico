package cadastro.clinica.api.controller;

import cadastro.clinica.api.domain.medico.DadosListagemMedicos;
import cadastro.clinica.api.domain.medico.Medico;
import cadastro.clinica.api.domain.medico.MedicoRepository;
import cadastro.clinica.api.domain.medico.*;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/medicos")
public class MedicoController {

    @Autowired
    private MedicoRepository repository;
    @PostMapping
    @Transactional
    public ResponseEntity cadastrar(@RequestBody @Valid DadosCadastroMedicos dadosCadastroMedicos, UriComponentsBuilder uriComponentsBuilder){
        //System.out.println(dadosCadastroMedicos);
        //  repository.save(new Medico(null, dadosCadastroMedicos.nome(), dadosCadastroMedicos.email(),dadosCadastroMedicos.crm(),new Endereco()));
        var medico = new Medico(dadosCadastroMedicos);
        repository.save(medico);
        var uri = uriComponentsBuilder.path("/medico/{id}").buildAndExpand(medico.getId()).toUri();
        return ResponseEntity.created(uri).body(new DadosDetalhamentoMedico(medico));
    }

//    @GetMapping
//    public List<DadosListagemMedicos> listar(){
//        return repository.findAll().stream().map(DadosListagemMedicos::new).toList();
//    }

//    @GetMapping
//    public Page<DadosListagemMedicos> listar(Pageable paginacao){
//        return repository.findAll(paginacao).map(DadosListagemMedicos::new);
//    }

    @GetMapping
    public ResponseEntity<Page<DadosListagemMedicos>> listar(Pageable paginacao){
        var page =  repository.findAllByAtivoTrue(paginacao).map(DadosListagemMedicos::new);
        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity atualizar(@RequestBody @Valid DadosAtualizaMedico dadosAtualizaMedico){
        var medico = repository.getReferenceById(dadosAtualizaMedico.id());
        medico.atualizarInformacoes(dadosAtualizaMedico);

        return  ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
    }

    //exclusação física
//    @DeleteMapping("/{id}")
//    @Transactional
//    public void excluir(@PathVariable Long id){
//        repository.deleteById(id);
//    }

    @DeleteMapping("/{id}")
     @Transactional
    public ResponseEntity excluir(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        medico.excluir();
        return ResponseEntity.noContent().build();
   }

   @GetMapping("/{id}")
    public ResponseEntity detalhar(@PathVariable Long id){
        var medico = repository.getReferenceById(id);
        return  ResponseEntity.ok(new DadosDetalhamentoMedico(medico));
   }
}
