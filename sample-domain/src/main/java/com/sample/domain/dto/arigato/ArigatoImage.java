package com.sample.domain.dto.arigato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.domain.dto.common.DomaDtoImpl;
import lombok.Getter;
import lombok.Setter;
import org.seasar.doma.*;

@Table(name = "arigato_images")
@Entity
@Getter
@Setter
public class ArigatoImage extends DomaDtoImpl{
    private static final long serialVersionUID = -5685671240451872150L;

    @OriginalStates // 差分UPDATEのために定義する
    @JsonIgnore // APIのレスポンスに含めない
    ArigatoImage originalStates;

    @Id
    @Column(name = "arigato_image_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long arigatoId;
    Long uploadFileId;
}
