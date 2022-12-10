package cn.neud.knownact.model.dto.user;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "user")
public class UserEmailDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "邮箱")
    private String email;


}
