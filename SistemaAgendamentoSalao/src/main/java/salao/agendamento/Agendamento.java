package salao.agendamento;

public class Agendamento {
    private String cliente;
    private String dataNascimento;
    private String telefone;
    private String servico;
    private String data;
    private String horario;

    public Agendamento(String cliente, String dataNascimento, String telefone, String servico, String data, String horario) {
        this.cliente = cliente;
        this.dataNascimento = dataNascimento;
        this.telefone = telefone;
        this.servico = servico;
        this.data = data;
        this.horario = horario;
    }

    // Métodos getters para acessar os valores privados
    public String getCliente() {
        return cliente;
    }

    public String getData() {
        return data;
    }

    public String getHorario() {
        return horario;
    }

    @Override
    public String toString() {
        return "Cliente: " + cliente + ", Data de Nascimento: " + dataNascimento + ", Telefone: " + telefone +
               ", Serviço: " + servico + ", Data: " + data + ", Horário: " + horario;
    }
}
