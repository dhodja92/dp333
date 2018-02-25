package hr.dp333.domain.dto;

import java.util.Collection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class DtoTransferObject<T> {

	private Collection<T> data;
	private Long totalRows;

}
