package org.quantum.rms.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/*
TODO: realize this entity after all
*/

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
//@Entity
public class Document {

//    @Id
//    @Column(name = "id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String name;

	private String path;

	private String type;

//    @ManyToOne
//    @JoinColumn(name = "route_id", referencedColumnName = "id")
//    private Route route;

}
