import java.util.Date;

public class Transacao {
    private Date data;
    private double valor;
    private String descricao;

    public Transacao(Date data, double valor, String descricao) {
        this.data = data;
        this.valor = valor;
        this.descricao = descricao;
    }

    public Transacao(double valor2, String string) {
    }

    public Date getData() {
        return data;
    }

    public double getValor() {
        return valor;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
    
    public String toString() {
        return "Transacao [data=" + data + ", valor=" + valor + ", descricao=" + descricao + "]";
    }
}
