package cadastro.clinica.api.domain.medico;

import cadastro.clinica.api.domain.endereco.Endereco;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Table(name = "medicos")
@Entity(name = "Medico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Medico {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nome;
    private String email;

    private String telefone;
    private String crm;

    @Enumerated(EnumType.STRING)
    private Especialidade especialidade;

    @Embedded
    private Endereco endereco;

    private Boolean ativo;

    public Medico(DadosCadastroMedicos dadosCadastroMedicos) {
        this.nome = dadosCadastroMedicos.nome();
        this.email = dadosCadastroMedicos.email();
        this.telefone = dadosCadastroMedicos.telefone();
        this.crm = dadosCadastroMedicos.crm();
        this.especialidade = dadosCadastroMedicos.especialidade();
        this.endereco = new Endereco(dadosCadastroMedicos.endereco());
        this.ativo = true;

    }

    public void atualizarInformacoes(DadosAtualizaMedico dadosAtualizaMedico) {

        if(dadosAtualizaMedico.nome() != null ){
            this.nome = dadosAtualizaMedico.nome();
        }

        if(dadosAtualizaMedico.telefone() != null ){
            this.telefone = dadosAtualizaMedico.telefone();
        }

        if(dadosAtualizaMedico.endereco() != null ){
            this.endereco.atualizarEndereco(dadosAtualizaMedico.endereco());
        }

    }

    public void excluir() {
        this.ativo = false;
    }
}
