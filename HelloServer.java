
/** HelloServer.java **/

import java.rmi.*;
import java.rmi.server.*;
import java.rmi.registry.*;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

public class HelloServer implements HelloWorld {
   private ArrayList<String> listaNickname;
   private ArrayList<String> listaMensagens;
   private int numeroUsuarios;
   private int numeroMensagens;

   public HelloServer() {
      listaNickname = new ArrayList<String>();
      listaMensagens = new ArrayList<String>();
   }

   public static void main(String[] args) {
      try {

         System.setProperty("java.rmi.server.hostname", args[0]);
         // Instancia o objeto servidor e a sua stub
         HelloServer server = new HelloServer();
         HelloWorld stub = (HelloWorld) UnicastRemoteObject.exportObject(server, 0);
         // Registra a stub no RMI Registry para que ela seja obtAida pelos clientes
         Registry registry = LocateRegistry.createRegistry(6600);
         //Registry registry = LocateRegistry.getRegistry(9999);
         registry.bind("Hello", stub);
         System.out.println("Servidor pronto");
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   public String hello() throws RemoteException {
      System.out.println("Executando hello()");
      return "Hello!!!";
   }

   public int conectar_usuario_1(String nickname) throws RemoteException {
      System.out.println(nickname + " solicitou conexão");

      int id_user;
      int id_usuario = novo_usuario(nickname);
      id_user = id_usuario;
      return id_user;
   }


   public int novo_usuario(String nickname) throws RemoteException{
      listaNickname.add(nickname);
      return numeroUsuarios++;
   }

   public int enviar_mensagem(estruturaMensagem msg) throws RemoteException {
      try {
         int resultadoFinal;
         listaMensagens.add(msg.getConteudo_mensagem());
         resultadoFinal = numeroMensagens++;

         Scanner leitor = new Scanner(System.in);
         String nomeDoArquivo = msg.getNickname() + "-0" + listaMensagens.size() + ".serv";

         FileWriter arq = new FileWriter(nomeDoArquivo);
         PrintWriter gravarArq = new PrintWriter(arq);

         gravarArq.printf(msg.getConteudo_mensagem());

         System.out.println("Arquivo enviado:");
         System.out.println(msg.getConteudo_mensagem());
         arq.close();

         return resultadoFinal;
      } catch (IOException e) {
         System.err.printf("Erro na abertura do arquivo: %s.\n",
         e.getMessage());
         return -1;
    }
   }

   // Retorna o número de mensagens a serem recebidas
   public int retornar_numero_mensagens() throws RemoteException {
      int consultaMensagens;
      consultaMensagens = numeroMensagens;
      return consultaMensagens;
   }

   // Resgata determinada mensagem
   public estruturaMensagem resgatar_mensagem(int idMensagem) throws RemoteException {
      estruturaMensagem msg = new estruturaMensagem();

      msg.setConteudo_mensagem(listaMensagens.get(idMensagem));

      return msg;
   }

   public String resgatar_usuario(int id_usuario) throws RemoteException {
      String resultadoConsulta;
      resultadoConsulta = listaNickname.get(id_usuario);
      return resultadoConsulta;
   }
}

