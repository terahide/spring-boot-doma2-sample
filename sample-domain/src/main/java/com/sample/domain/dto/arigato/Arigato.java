package com.sample.domain.dto.arigato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.domain.dto.common.DomaDtoImpl;
import com.sample.domain.dto.system.UploadFile;
import lombok.Getter;
import lombok.Setter;
import org.seasar.doma.*;

import java.util.ArrayList;
import java.util.List;

@Table(name = "arigatos")
@Entity
@Getter
@Setter
public class Arigato extends DomaDtoImpl {
    private static final long serialVersionUID = -5353424879332700470L;

    @OriginalStates // 差分UPDATEのために定義する
    @JsonIgnore // APIのレスポンスに含めない
    Arigato originalStates;

    @Id
    @Column(name = "arigato_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long fromId;
    Long toId;
    String subject;
    String body;

    @Transient
    @JsonIgnore
    List<Long> uploadFileId;

    // 添付ファイル
    @Transient // Domaで永続化しない
    @JsonIgnore
    List<UploadFile> uploadFile;

    public Arigato() {
        super();
        this.uploadFile = new ArrayList<>();
        this.uploadFileId = new ArrayList<>();
    }
}
