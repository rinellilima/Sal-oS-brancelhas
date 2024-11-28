package salao.agendamento;

import salao.agendamento.view.AgendamentoSwingUI;

public class Main {
    public static void main(String[] args) {
        SistemaAgendamento sistema = new SistemaAgendamento();
        new AgendamentoSwingUI(sistema);
    }
}
