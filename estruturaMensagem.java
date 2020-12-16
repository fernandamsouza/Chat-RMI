import java.io.Serializable;

public class estruturaMensagem implements Serializable {

    private String nickname;
    private int id_usuario;
    private String conteudo_mensagem;

    public estruturaMensagem() {

    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getConteudo_mensagem() {
        return conteudo_mensagem;
    }

    public void setConteudo_mensagem(String conteudo_mensagem) {
        this.conteudo_mensagem = conteudo_mensagem;
    }

    public int getId_usuario() {
        return id_usuario;
    }

    public void setId_usuario(int id_usuario) {
        this.id_usuario = id_usuario;
    }
}