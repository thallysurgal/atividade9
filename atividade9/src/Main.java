import javax.swing.*;
import java.util.*;

class CampeonatoFutebol {
    static class Time {
        String nome;
        int pontos, vitorias, empates, derrotas, golsPro, golsContra;

        public Time(String nome) {
            this.nome = nome;
        }
    }

    static ArrayList<Time> times = new ArrayList<>();
    static ArrayList<String> jogosRealizados = new ArrayList<>();

    public static void main(String[] args) {
        while(true) {
            String opcao = JOptionPane.showInputDialog(
                    "CAMPEONATO DE FUTEBOL\n\n" +
                            "1. Cadastrar times\n" +
                            "2. Jogar partida\n" +
                            "3. Ver tabela\n" +
                            "4. Sair\n\n" +
                            "Escolha uma opção:");

            if(opcao == null) System.exit(0);

            switch(opcao) {
                case "1": cadastrarTimes(); break;
                case "2": jogarPartida(); break;
                case "3": mostrarTabela(); break;
                case "4": System.exit(0);
                default: JOptionPane.showMessageDialog(null, "Opção inválida!");
            }
        }
    }

    static void cadastrarTimes() {
        if(times.size() >= 10) {
            JOptionPane.showMessageDialog(null, "Limite de 10 times atingido!");
            return;
        }

        String nome = JOptionPane.showInputDialog("Digite o nome do time:");
        if(nome != null && !nome.trim().isEmpty()) {
            times.add(new Time(nome));
            JOptionPane.showMessageDialog(null, "Time cadastrado com sucesso!");
        }
    }

    static void jogarPartida() {
        if(times.size() < 2) {
            JOptionPane.showMessageDialog(null, "Cadastre pelo menos 2 times!");
            return;
        }

        Time time1 = selecionarTime("Selecione o primeiro time:");
        Time time2 = selecionarTime("Selecione o segundo time:");

        if(time1 == time2) {
            JOptionPane.showMessageDialog(null, "Selecione times diferentes!");
            return;
        }

        String chaveJogo = time1.nome + " vs " + time2.nome;
        if(jogosRealizados.contains(chaveJogo)) {
            JOptionPane.showMessageDialog(null, "Este jogo já foi realizado!");
            return;
        }

        int gols1 = new Random().nextInt(5);
        int gols2 = new Random().nextInt(5);

        // Atualiza estatísticas
        if(gols1 > gols2) {
            time1.pontos += 3;
            time1.vitorias++;
            time2.derrotas++;
        } else if(gols2 > gols1) {
            time2.pontos += 3;
            time2.vitorias++;
            time1.derrotas++;
        } else {
            time1.pontos++;
            time2.pontos++;
            time1.empates++;
            time2.empates++;
        }

        time1.golsPro += gols1;
        time1.golsContra += gols2;
        time2.golsPro += gols2;
        time2.golsContra += gols1;

        jogosRealizados.add(chaveJogo);
        JOptionPane.showMessageDialog(null, "Resultado: " + time1.nome + " " + gols1 + " x " + gols2 + " " + time2.nome);
    }

    static Time selecionarTime(String mensagem) {
        String[] nomes = new String[times.size()];
        for(int i = 0; i < times.size(); i++) {
            nomes[i] = times.get(i).nome;
        }

        String selecionado = (String)JOptionPane.showInputDialog(
                null, mensagem, "Times",
                JOptionPane.PLAIN_MESSAGE, null, nomes, nomes[0]);

        for(Time t : times) {
            if(t.nome.equals(selecionado)) return t;
        }
        return null;
    }

    static void mostrarTabela() {
        // Ordena por pontos e saldo de gols
        times.sort((a, b) -> {
            if(b.pontos != a.pontos) return b.pontos - a.pontos;
            return (b.golsPro - b.golsContra) - (a.golsPro - a.golsContra);
        });

        StringBuilder tabela = new StringBuilder();
        tabela.append("POS | TIME          | PTS | J | V | E | D | GP | GC | SG\n");
        tabela.append("--------------------------------------------------------\n");

        for(int i = 0; i < times.size(); i++) {
            Time t = times.get(i);
            int jogos = t.vitorias + t.empates + t.derrotas;
            int saldo = t.golsPro - t.golsContra;

            tabela.append(String.format("%2d  | %-12s | %3d | %1d | %1d | %1d | %1d | %2d | %2d | %3d\n",
                    i+1, t.nome, t.pontos, jogos, t.vitorias, t.empates, t.derrotas, t.golsPro, t.golsContra, saldo));
        }

        JOptionPane.showMessageDialog(null, tabela.toString(), "Tabela do Campeonato", JOptionPane.PLAIN_MESSAGE);
    }
}