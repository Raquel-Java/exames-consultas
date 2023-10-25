package br.com.exames.infrastructure.config;

import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import br.com.exames.dto.ExameDto;
import br.com.exames.entity.Exame;

@Configuration
public class ConfiguracaoModelMapper {

	@Bean("obterModelMapper")
	public ModelMapper configurarModelMapper() {
		
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		
		Converter<String, Long> stringToLongConverter = context -> {
			if (context.getSource() == null) {
				return null;
			}
			return Long.valueOf(context.getSource());
		};
		modelMapper.addConverter(stringToLongConverter);
		modelMapper.createTypeMap(ExameDto.class, Exame.class);
		return modelMapper;
	}


}
