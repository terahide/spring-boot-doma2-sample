package com.sample.domain.dto.arigato;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sample.domain.dto.common.DomaDtoImpl;
import lombok.Getter;
import lombok.Setter;
import org.seasar.doma.*;

@Table(name = "favorites")
@Entity
@Getter
@Setter
public class Fav extends DomaDtoImpl {
    private static final long serialVersionUID = 883483248556542464L;

    @OriginalStates // 差分UPDATEのために定義する
    @JsonIgnore // APIのレスポンスに含めない
    Fav originalStates;

    @Id
    @Column(name = "favorite_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    Long arigatoId;
    Long userId;
}
