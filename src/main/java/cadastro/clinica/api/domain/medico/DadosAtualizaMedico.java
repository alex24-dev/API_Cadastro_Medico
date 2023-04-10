package cadastro.clinica.api.domain.medico;

import cadastro.clinica.api.domain.endereco.DadosEndereco;
import jakarta.validation.constraints.NotNull;

public record DadosAtualizaMedico(
        @NotNull
        Long id,
        String nome,

        String telefone,

        DadosEndereco endereco) {
}
