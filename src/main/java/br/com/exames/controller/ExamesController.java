package br.com.exames.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

import br.com.exames.entity.Exame;
import br.com.exames.infrastructure.config.FileStorageProperties;
import br.com.exames.infrastructure.swagger.ExameRecursoDoc;
import br.com.exames.service.ExameService;
import io.swagger.annotations.ApiParam;

@Controller
@RequestMapping("v1/exames")
public class ExamesController implements ExameRecursoDoc {
	private final Path fileStorageLocation;

	@Autowired
	private ExameService servico;

	public ExamesController(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
	}

	@SuppressWarnings("unchecked")
	@PostMapping(value = "upload", produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE })
	public ResponseEntity uploadFile(
			@ApiParam(value = "File to be uploaded", required = true) @RequestPart("file") MultipartFile file,
			@RequestParam(required = false, defaultValue = "01/01/1999", name = "Data Exame") 
			@DateTimeFormat(pattern = "yyyy-MM-dd") String dataRealizacao,
			@RequestParam(required = false, defaultValue = "", name = "Usuario") String nmUsuario,
			@RequestParam(required = true, defaultValue = "", name = "C.P.F") String cpfPaciente,
			@RequestParam(required = false, defaultValue = "", name = "CRM") String crmMedico,
			@RequestParam(required = false, defaultValue = "", name = "Parte-Corpo") String parteCorpo,
			@RequestParam(required = false, defaultValue = "", name = "Indicacao") String indicacao) {

		try {
			return ResponseEntity.ok(this.servico.salvarExame(file, dataRealizacao, nmUsuario, cpfPaciente, crmMedico,
					parteCorpo, indicacao));
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}

	}

	@GetMapping("/download/{fileName:.+}")
	public ResponseEntity<Resource> downloadFile(@PathVariable String fileName, HttpServletRequest request)
			throws IOException {
		Path filePath = fileStorageLocation.resolve(fileName).normalize();
		try {
			Resource resource = new UrlResource(filePath.toUri());

			String contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
			if (contentType == null) {
				contentType = "application/octet-stream";
				return ResponseEntity.noContent().build();
			}else {
				return ResponseEntity.ok().contentType(MediaType.parseMediaType(contentType))
						.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
						.body(resource);
			}
		} catch (MalformedURLException ex) {
			return ResponseEntity.badRequest().build();
		}
	}

	@GetMapping("/list")
	public ResponseEntity<List<String>> listArquivosServidor() throws IOException {
		List<String> fileNames = Files.list(fileStorageLocation).map(Path::getFileName).map(Path::toString)
				.collect(Collectors.toList());
		if (fileNames == null) {
			return ResponseEntity.noContent().build();
		}else {
			return ResponseEntity.ok().header("Sucesso na requisição").body(fileNames);
		}
	}
	
	@GetMapping("/prontuario")
	public ResponseEntity listExamesPaciente(@RequestParam(required = true, 
			defaultValue = "", name = "C.P.F") String cpf) throws IOException {
		try {
			return ResponseEntity.ok().header("Sucesso na requisição").body(servico.listExame(cpf));
		} catch (Exception e) {
			return ResponseEntity.noContent().build();
		}
	}
}
