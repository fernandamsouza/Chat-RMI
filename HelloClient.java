/** HelloClient.java **/
import java.rmi.registry.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.io.File;
import java.util.Arrays;
import java.io.PrintWriter;

public class HelloClient {
   private static int numeroMensagem = 1;
   private static int id_usuario;

   public static void contadorNumeroMensagem() {
      HelloClient.numeroMensagem++;
   }

   public static void main(String[] args) {
      String host = args[0];
      String nickname;
   

      try {
         // Obtém uma referência para o registro do RMI
         Registry registry = LocateRegistry.getRegistry(host,6600);
         
         // Passagem do parâmetro nickname para a string
         nickname = args[1];

         // Obtém a stub do servidor
         HelloWorld stub = (HelloWorld) registry.lookup("Hello");

         // Conectar ao RMI e retornar um id único para identificação do usuário
         HelloClient.id_usuario = stub.conectar_usuario_1(nickname);

         System.out.println("\nRMI - Fernanda Maria de Souza\n");

         int opcao = 0;

         // Menu para navegação
         do {
            opcao = menu();
            switch(opcao) {
            case 0:
               break;
            case 1:
               // Envio de mensagens para o servidor
               envio_mensagens(HelloClient.id_usuario, args[1], stub);
               break;
            case 2:
               // Para receber as mensagens, os clientes devem consultar o servidor de tempos em tempos (polling)
               recebimento_mensagens(stub);
               break;
            default:
               break;
            }
         } while (opcao != 0);

         // Chama o método do servidor e imprime a mensagem
         String msg = stub.hello();
         System.out.println("Mensagem do Servidor: " + msg);
      } catch (Exception ex) {
         ex.printStackTrace();
      }
   }

   private static int menu() {
      int num;
      Scanner leitor = new Scanner(System.in);

      System.out.println("\n------------------------------");
      System.out.println("\n1 - Enviar mensagem\n");
      System.out.println("\n2 - Receber mensagem\n");
      System.out.println("\n0 - Sair\n");
      System.out.println("\n------------------------------");

      num = leitor.nextInt();

      return num;
   }

   private static void envio_mensagens(int id_usuario, String nickname, HelloWorld stub) {
      estruturaMensagem msg = new estruturaMensagem();
      StringBuilder builder = new StringBuilder();
      
      String file_content;
      String nomeArquivo;
      
      Scanner leitor = new Scanner(System.in);
      try {
         System.out.printf("Informe o nome do arquivo:\n");
         String nome = leitor.nextLine();

        	BufferedReader buffRead = new BufferedReader(new FileReader(nome));
         String linha = "";
         while (true) {
            if (linha != null) {
               System.out.println(linha);

            } else
               break;
            linha = buffRead.readLine();
            builder.append(linha);
         }
         buffRead.close();

         msg.setNickname(nickname);
         msg.setConteudo_mensagem(builder.toString());
         msg.setId_usuario(id_usuario);

         stub.enviar_mensagem(msg);

      } catch (IOException e) {
        System.err.printf("Erro na abertura do arquivo: %s.\n",
          e.getMessage());
    }
   }

   private static void recebimento_mensagens(HelloWorld stub) {
      try {
         estruturaMensagem msg = new estruturaMensagem();
         int i = 0;
         int mensagens = 0;

         mensagens = stub.retornar_numero_mensagens();

         for (i = 0; i < mensagens; i++) {
            // Resgate no servidor da mensagem para retransmissão aos clientes conectados
            msg = stub.resgatar_mensagem(i);

            // Resgate no servidor do usuário atual
            String user = stub.resgatar_usuario(HelloClient.id_usuario);

            // Adequação do nome do arquivo conforme descrição do trabalho
            String nomeDoArquivo = user + "-0" + HelloClient.numeroMensagem + ".client0" + (HelloClient.id_usuario+1);

            // Incrementa o numero de mensagens
            contadorNumeroMensagem();

            Scanner leitor = new Scanner(System.in);

            FileWriter arq = new FileWriter(nomeDoArquivo);
            PrintWriter gravarArq = new PrintWriter(arq);

            gravarArq.printf(msg.getConteudo_mensagem());

            System.out.println("Arquivo enviado:");
            System.out.println(msg.getConteudo_mensagem());
            arq.close();
         }
      } catch (IOException e) {
         System.err.printf("Erro na abertura do arquivo: %s.\n",
         e.getMessage());
    }
   }
}
