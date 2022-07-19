package com.soulcode.Servicos.Util;

import org.apache.tomcat.jni.File;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


// Upload do arquivo

public class UploadFile {

    // uploadDir é o caminho o qual vai ser salvo o arquivo // fileName é o nome q se colocará no arquivo
    public static void saveFile(String uploadDir, String fileName, MultipartFile file) throws IOException { //MultipartFile - arquivo que será feito o upload

        Path uploadPath = Paths.get(uploadDir); // fez a leitura do caminho

        if(!Files.exists(uploadPath)){ //caso não exista o caminho, ele vai criar um
            Files.createDirectories(uploadPath); // aqui ele cira o diretório caso n exista. Ele vai pedir para acrescentar uma exceção e irá lá para cima com o throws
        }
        //sempre que vier um throw tem que ter um try
        // Aqui vamos tentar fazer o upload do arquivo
        //InpuStream - tentar fazer a leitura do arquivo que queremos subir
        //getInputStream - faz a leitura byte por byte do arquivo
        try(InputStream inputStream = file.getInputStream()){ //classe que faz a leitura do arquivo que será feito o upload

            //nesse momento o arquivo é salvo no diretório  qur passamos na assinatura do método
            Path filePath = uploadPath.resolve(fileName); //instancia um novo objeto para path e a partir do objeto anterior uploadPath usar o resolve para ler o arquivo e subir
            Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING); // faz uma cópia do arquivo no servidor (local q esta sendo fdeito o upload). O Replace_existing, substitui caso haja outro arquivo

        }

        catch (IOException e){
            throw new IOException("Não foi possível enviar o seu arquivo");
        }

    }

    //Depois de criar o serviço, precisa mappeá-lo para ser acessadd
}
