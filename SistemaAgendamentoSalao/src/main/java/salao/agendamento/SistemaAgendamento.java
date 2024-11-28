package salao.agendamento;

import java.util.ArrayList;

public class SistemaAgendamento {
    private ArrayList<Agendamento> agendamentos = new ArrayList<>();

    public void adicionarAgendamento(Agendamento agendamento) {
        agendamentos.add(agendamento);
    }

    public ArrayList<Agendamento> getAgendamentos() {
        return agendamentos;
    }
}
