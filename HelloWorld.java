/** HelloWorld.java **/
import java.rmi.*;
 
public interface HelloWorld extends Remote {
   public String hello() throws RemoteException;

   public int conectar_usuario_1(String nickname) throws RemoteException;

   public int novo_usuario(String nickname) throws RemoteException;

   public int enviar_mensagem(estruturaMensagem msg) throws RemoteException;

   public int retornar_numero_mensagens() throws RemoteException;

   public estruturaMensagem resgatar_mensagem(int idMensagem) throws RemoteException;

   public String resgatar_usuario(int id_usuario) throws RemoteException;
}
