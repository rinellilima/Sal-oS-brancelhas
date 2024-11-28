package salao.agendamento.view;

import salao.agendamento.Agendamento;
import salao.agendamento.SistemaAgendamento;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import com.toedter.calendar.JDateChooser; // Importando JDateChooser

public class AgendamentoSwingUI extends JFrame {
    private SistemaAgendamento sistema;
    private JTextField clienteField;
    private JFormattedTextField telefoneField, dataNascimentoField;
    private JComboBox<String> horarioComboBox, servicoComboBox; // ComboBox para serviços
    private JTextArea mensagemArea;  // Para exibir a mensagem do agendamento realizado
    private JDateChooser dataAgendamentoChooser;  // Calendário para selecionar a data do agendamento

    // Cor personalizada
    private final Color redColor = new Color(0xbf1313);
    private final Color whiteColor = Color.WHITE;

    public AgendamentoSwingUI(SistemaAgendamento sistema) {
        this.sistema = sistema;
        setTitle("Agendamento Sobrancelhas");
        setSize(400, 650);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents();
        setVisible(true);
    }

    private void initComponents() {
        // Configuração do layout do painel principal
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBackground(whiteColor);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setContentPane(mainPanel);

        // Título
        JLabel titleLabel = new JLabel("Agendamento Sobrancelhas", JLabel.CENTER);
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 24));
        titleLabel.setForeground(redColor);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Painel de entrada
        JPanel inputPanel = new JPanel(new GridBagLayout());
        inputPanel.setBackground(whiteColor);
        mainPanel.add(inputPanel, BorderLayout.CENTER);
        
        // Constraints para GridBagLayout
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Adicionando componentes ao painel
        gbc.gridx = 0; gbc.gridy = 0; inputPanel.add(createLabel("Nome do Cliente:"), gbc);
        gbc.gridx = 1; gbc.gridy = 0; clienteField = new JTextField(); 
        clienteField.setPreferredSize(new Dimension(150, 40)); // A largura e altura
        inputPanel.add(clienteField, gbc);

        gbc.gridx = 0; gbc.gridy = 1; inputPanel.add(createLabel("Data de Nascimento (dd/mm/aaaa):"), gbc);
        gbc.gridx = 1; gbc.gridy = 1; dataNascimentoField = createDataField(); 
        dataNascimentoField.setPreferredSize(new Dimension(150, 40)); // A largura e altura
        inputPanel.add(dataNascimentoField, gbc);

        gbc.gridx = 0; gbc.gridy = 2; inputPanel.add(createLabel("Telefone (xx) xxxxx-xxxx:"), gbc);
        gbc.gridx = 1; gbc.gridy = 2; telefoneField = createTelefoneField(); 
        telefoneField.setPreferredSize(new Dimension(150, 40)); // A largura e altura
        inputPanel.add(telefoneField, gbc);

        // Adicionando o JComboBox para os serviços
        String[] servicos = {
            "Aplicação de Henna", "Aplicação de Tintura", "Brow lamination", 
            "Design de sobrancelhas", "Extensão de Cílios", "Higienização Facial", 
            "Lash Lifting", "Limpeza de Pele"
        };
        java.util.Arrays.sort(servicos); // Ordenar os serviços alfabeticamente
        
        gbc.gridx = 0; gbc.gridy = 3; inputPanel.add(createLabel("Serviço Desejado:"), gbc);
        gbc.gridx = 1; gbc.gridy = 3; servicoComboBox = new JComboBox<>(servicos);
        servicoComboBox.setSelectedIndex(0); // Pré-seleção do primeiro serviço
        servicoComboBox.setPreferredSize(new Dimension(150, 40));
        inputPanel.add(servicoComboBox, gbc);

        // Adicionando o JDateChooser para a seleção da data
        gbc.gridx = 0; gbc.gridy = 4; inputPanel.add(createLabel("Data do Agendamento:"), gbc);
        gbc.gridx = 1; gbc.gridy = 4; 
        dataAgendamentoChooser = new JDateChooser();
        dataAgendamentoChooser.setPreferredSize(new Dimension(150, 40));
        inputPanel.add(dataAgendamentoChooser, gbc);

        gbc.gridx = 0; gbc.gridy = 5; inputPanel.add(createLabel("Horário:"), gbc);
        gbc.gridx = 1; gbc.gridy = 5; 
        horarioComboBox = new JComboBox<>(new String[]{
            "10:00", "10:40", "11:20", "12:00", "12:40", "13:20", 
            "14:00", "14:40", "15:20", "16:00", "16:40", "17:20", 
            "18:00", "18:40", "19:20", "20:00", "20:40", "21:20"
        });
        inputPanel.add(horarioComboBox, gbc);

        // Botão de Adicionar Agendamento
        JButton addButton = new JButton("Adicionar Agendamento");
        addButton.setBackground(redColor);
        addButton.setForeground(whiteColor);
        addButton.setFocusPainted(false);
        addButton.setBorderPainted(false);
        addButton.setOpaque(true);
        addButton.setPreferredSize(new Dimension(150, 40));
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarAgendamento();
            }
        });
        gbc.gridx = 0; gbc.gridy = 6; gbc.gridwidth = 2; inputPanel.add(addButton, gbc);

        // Ajuste da área de mensagem - Exibir agendamento
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; // Aqui garantimos que a área de mensagem tenha espaço abaixo do botão
        mensagemArea = new JTextArea();
        mensagemArea.setEditable(false);
        mensagemArea.setBackground(Color.LIGHT_GRAY);
        mensagemArea.setForeground(Color.BLACK); // Cor da mensagem agora é preta
        mensagemArea.setBorder(BorderFactory.createLineBorder(redColor));
        mensagemArea.setPreferredSize(new Dimension(310, 120));  // Largura aumentada em 10px
        inputPanel.add(mensagemArea, gbc);
    }

    private JFormattedTextField createTelefoneField() {
        try {
            MaskFormatter formatter = new MaskFormatter("(##) #####-####");
            formatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private JFormattedTextField createDataField() {
        try {
            MaskFormatter formatter = new MaskFormatter("##/##/####");
            formatter.setPlaceholderCharacter('_');
            return new JFormattedTextField(formatter);
        } catch (ParseException e) {
            e.printStackTrace();
            return new JFormattedTextField();
        }
    }

    private JLabel createLabel(String text) {
        JLabel label = new JLabel(text);
        label.setForeground(redColor);
        return label;
    }

    private void adicionarAgendamento() {
        String cliente = clienteField.getText();
        String dataNascimento = dataNascimentoField.getText();
        String telefone = telefoneField.getText();
        String servico = (String) servicoComboBox.getSelectedItem();
        
        // Pegando a data selecionada no calendário
        java.util.Date data = dataAgendamentoChooser.getDate();
        
        // Formatando a data para o formato brasileiro
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataFormatada = sdf.format(data);
        
        String horario = (String) horarioComboBox.getSelectedItem();

        Agendamento agendamento = new Agendamento(cliente, dataNascimento, telefone, servico, dataFormatada, horario);
        sistema.adicionarAgendamento(agendamento);

        // Exibir mensagem de sucesso e os detalhes do agendamento
        exibirMensagemDeSucesso(cliente, servico, dataFormatada, horario);
        limparCampos();
    }

    private void exibirMensagemDeSucesso(String cliente, String servico, String data, String horario) {
        String mensagem = "Agendamento realizado com sucesso!\n\n"
                          + "Cliente: " + cliente + "\n"
                          + "Serviço: " + servico + "\n"
                          + "Data de Agendamento: " + data + "\n"
                          + "Horário: " + horario;
        mensagemArea.setText(mensagem);
    }

    private void limparCampos() {
        clienteField.setText("");
        dataNascimentoField.setText("");
        telefoneField.setText("");
        servicoComboBox.setSelectedIndex(0);
        dataAgendamentoChooser.setDate(null);
        horarioComboBox.setSelectedIndex(0);
    }
}