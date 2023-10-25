package br.com.exames.service;

import java.io.IOException;
import java.math.BigInteger;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.UUID;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.com.exames.dto.ExameDto;
import br.com.exames.dto.ResponseObject;
import br.com.exames.entity.Exame;
import br.com.exames.infrastructure.config.FileStorageProperties;
import br.com.exames.repository.ExameDao;

@Service
public class ExameService {

	@Autowired
	private ExameDao exameDao;

	@Autowired
	private ModelMapper modelMapper;

	private final Path fileStorageLocation;

	public ExameService(FileStorageProperties fileStorageProperties) {
		this.fileStorageLocation = Paths.get(fileStorageProperties.getUploadDir()).toAbsolutePath().normalize();
	}

	public Optional<ResponseObject> salvarExame(MultipartFile file, String dtExame, String usuario, String cpf,
			String crm, String parteCorpo, String indicacao) {
		ExameDto exame = new ExameDto();
		DateTimeFormatter parser = DateTimeFormatter.ofPattern("dd/MM/uuuu");
		LocalDateTime dataExame = LocalDate.parse(dtExame, parser).atStartOfDay();

		ResponseObject resposta = new ResponseObject();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		String output = uuid(fileName);

		fileName = output + "-" + StringUtils.cleanPath(file.getOriginalFilename().toUpperCase());
		Path targetLocation = fileStorageLocation.resolve(fileName);

		exame.setDtRealizacaoExame(dataExame);
		exame.setNmUsuario(usuario);
		exame.setCpfPaciente(cpf);
		exame.setCrmMedicoExaminador(crm);
		exame.setParteCorpo(parteCorpo);
		exame.setIndicacao(indicacao);
		exame.setCodExame(output);
		exame.setNome(file.getOriginalFilename().toUpperCase());
		exame.setNomeHash(fileName);
		exame.setUrl(ServletUriComponentsBuilder.fromCurrentContextPath().path("/v1/exames/download/").path(fileName)
				.toUriString());

		Exame exameMap = modelMapper.map(exame, Exame.class);

		try {
			file.transferTo(targetLocation);
			try {
				exameMap = exameDao.save(exameMap);
			} catch (Exception e) {
				resposta.getErro(exameMap);
			}

			resposta.getSucesso(exameMap);
		} catch (IOException e) {
			resposta.setObj(exameMap);
		}

		return Optional.ofNullable(resposta);

	}

	private String uuid(String fileName) {
		String randomValue = UUID.randomUUID().toString();
		String input = randomValue + fileName;
		MessageDigest digest = null;
		try {
			digest = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}

		byte[] inputBytes = input.getBytes();
		digest.update(inputBytes, 0, inputBytes.length);
		byte[] md5sum = digest.digest();
		BigInteger bigInt = new BigInteger(1, md5sum);
		String output = bigInt.toString(16);

		return output;

	}

}
