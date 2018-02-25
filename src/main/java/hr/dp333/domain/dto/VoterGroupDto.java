package hr.dp333.domain.dto;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

@Getter
@AllArgsConstructor
@ToString (of = {"party", "voters", "activeStr"})
public class VoterGroupDto implements Serializable {

	private static final long serialVersionUID = -3517702584270497377L;

	private String party;
	private int voters;
	private boolean active;
	private String activeStr;

}
