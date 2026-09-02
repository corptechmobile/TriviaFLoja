package br.com.webapp.web.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.SocketAddress;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;

import javax.imageio.ImageIO;

public class UtilImagem {

	public static String DIRETORIO_IMAGENS_GRADE_MAIS_GIRO = "http://177.38.204.10/3050:C:\\Program Files\\Trivia\\Client\\ImgProd\\";

	public static boolean verificarImagemExistenteServidor(String diretorio, int codigoEmpresa, String nomeImagem) {
		// Verificar se arquivo existe no diretorio.
		StringBuilder caminhoImagem = new StringBuilder();
		caminhoImagem.append(getURLServidor())
			.append(diretorio)
			.append(File.separator)
			.append(String.valueOf(codigoEmpresa))
			.append(File.separator)
			.append(nomeImagem);
		
		File file = new File(caminhoImagem.toString());

		return file.exists();
	}

	public static void salvarImagemDiretorioServidorGrade(String urlImagemCliente, String nomeImagem, int empresa) throws IOException {

//		URL url = new URL(urlImagemCliente + nomeImagem);
//
//		BufferedImage imagem = ImageIO.read(url.openStream());
//
//		String decoded = getURLServidor();
//
//		String caminho = "../../uploads/grade/" + String.valueOf(empresa);
//
//		// Pegar url do servidor + nome da imagem
//		String gradePath = decoded + caminho + File.separator + nomeImagem;
//
//		// Salva a imagem no destino
//		ImageIO.write(imagem, "jpg", new File(gradePath));
		
		// This is where you'd define the proxy's host name and port.
        SocketAddress address = new InetSocketAddress("wsmobile.com.br", 3050);
        
        // Create an HTTP Proxy using the above SocketAddress.
        Proxy proxy = new Proxy(Proxy.Type.HTTP, address);
        
        //URL url = new URL(urlImagemCliente + nomeImagem);
        
        URL url = new URL("http://177.38.204.10/3050:C:\\Program Files\\Trivia\\Data\\20g_A.jpg");
        
        
        
        // Open a connection to the URL using the proxy information.
        URLConnection conn = url.openConnection(proxy);
        InputStream inStream = conn.getInputStream();
        
        // BufferedImage image = ImageIO.read(url);
        // Use the InputStream flavor of ImageIO.read() instead.
        BufferedImage image = ImageIO.read(inStream);
        
        ImageIO.write(image, "JPG", new File("image.jpg"));

	}

	public static void salvarImagemDiretorioServidorLinhaProduto(String urlImagemCliente, String nomeImagem, int codigo_empresa)
			throws IOException {

		URL url = new URL(urlImagemCliente);

		BufferedImage imagem = ImageIO.read(url);

		String decoded = getURLServidor();

		String caminho = "../../uploads/linha_produto/" + String.valueOf(codigo_empresa);

		// Pegar url do servidor + nome da imagem
		String gradePath = decoded + caminho + File.separator + nomeImagem;

		// Salva a imagem no destino
		ImageIO.write(imagem, "jpg", new File(gradePath));

	}

	/*
	 * - Pega o diretório de acordo com a localização do arquivo que o método está incluso.
	 */
	public static String getURLServidor() {

		String decoded = "";

		URL r = UtilImagem.class.getResource("/");

		try {
			decoded = URLDecoder.decode(r.getFile(), "UTF-8");
			if (decoded.startsWith("/")) {
				decoded = decoded.replaceFirst("/", ""); // output - "C:/Program Files/Tomcat 6.0/webapps/myapp/WEB-INF/classes/"
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return decoded;
	}
}
