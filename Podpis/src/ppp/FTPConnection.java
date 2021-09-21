package ppp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;

public class FTPConnection {
// xxxxxxxxxxxxxxxxxxx IP Login i hasol
	String server = "xx.xxx.xx.xx IP SERWERA";
	int port = 21;
	String user = "uzytkownik";
	String pass = "haslo";
	
	String komunikatFTP = "";
	boolean zapisNaFTPUdany = false;

	public boolean isZapisNaFTPUdany() {
		return zapisNaFTPUdany;
	}

	public void setZapisNaFTPUdany(boolean zapisUdany) {
		this.zapisNaFTPUdany = zapisUdany;
	}

	public String getKomunikatFTP() {
		return komunikatFTP;
	}

	public void setKomunikatFTP(String komunikat) {
		this.komunikatFTP = komunikat;
	}

	public void zapisNaFTP(String nazwaPlikuNaFTP) {
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(server, port);
			ftpClient.login(user, pass);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			File secondLocalFile = new File("C:/podglad/robocze.jpg");
			
		//	jTAwypisInformacji.append("\n" + dataPelna + ": Ankieta aktywna do wype³nienia");
			String secondRemoteFile = "Skany/" + nazwaPlikuNaFTP;
			InputStream inputStream = new FileInputStream(secondLocalFile);

			System.out.println("Rozpoczynam zapis pliku na FTP");
			
			OutputStream outputStream = ftpClient.storeFileStream(secondRemoteFile);
			byte[] bytesIn = new byte[4096];
			int read = 0;

			while ((read = inputStream.read(bytesIn)) != -1) {
				outputStream.write(bytesIn, 0, read);
			}
			inputStream.close();
			outputStream.close();

			boolean completed = ftpClient.completePendingCommand();
			if (completed) {
				setKomunikatFTP("Zapis Pliku na FTP udany");
				System.out.println("Zapis Pliku na FTP udany.");
				setZapisNaFTPUdany(true);
			}

		} catch (IOException ex) {
			setKomunikatFTP("B³¹d zapisu na FTP: " + ex.getMessage());
			System.out.println("B³¹d zapisu na FTP: " + ex.getMessage());
			setZapisNaFTPUdany(false);
			ex.printStackTrace();
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
