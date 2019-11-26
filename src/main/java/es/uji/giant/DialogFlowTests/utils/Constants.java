package es.uji.giant.DialogFlowTests.utils;

public class Constants {
    public static final String WELCOME_INTENT = "MyWelcomeIntent";
    public static final String CITY_INTENT = "Pregunta Ciudad";
    public static final String SEX_INTENT = "Pregunta Genero";
    public static final String AGE_INTENT = "Pregunta Edad";
    public static final String ALONE_INTENT = "Pregunta Solo";
    public static final String JONG1_INTENT = "Jong 1";
    public static final String JONG2_INTENT = "Jong 2";
    public static final String JONG3_INTENT = "Jong 3";
    public static final String JONG4_INTENT = "Jong 4";
    public static final String JONG5_INTENT = "Jong 5";
    public static final String JONG6_INTENT = "Jong 6";
    public static final String UCLA1_INTENT = "UCLA 1";
    public static final String UCLA2_INTENT = "UCLA 2";
    public static final String UCLA3_INTENT = "UCLA 3";
    public static final String USER_COMMENT_INTENT = "Comentarios Usuario";

    public static final String NOT_VALID_CITY_ANSWER = "No existe el municipio. Por favor, compruebe el nombre y vuelva a introducirlo.";
    public static final String NOT_VALID_SEX_ANSWER = "Tiene que indicar un género válido (Femenino, Masculino, Otro, Prefiero no contestar)";
    public static final String NOT_VALID_AGE_ANSWER = "Tiene que indicar un número válido entre 18 y 105 (Por ejemplo: 67). Si lo prefiere puede indicar Prefiero no contestar";
    public static final String NOT_VALID_ALONE_ANSWER = "Tiene que indicar Sí, No, Prefiero no contestar";
    public static final String NOT_VALID_JONG_ANSWER = "Su respuesta debe ser Sí, No, Más o menos";
    public static final String NOT_VALID_UCLA_ANSWER = "Su respuesta debe ser Casi nunca, Algunas veces, A menudo";
    public static final String CANCEL_CONVERSATION_OUTPUT = "Operación cancelada... ¿En que le puedo ayudar?";
    public static final String FINISHED_CONVERSATION_OUTPUT_ANSWER = "Muchas gracias, ¡Hemos terminado!";

    public static final String ELASTICSEARCH_TEST_INDEX = "giant_tests";
    public static final String ELASTICSEARCH_TEST_TYPE = "tests";

    // https://es.wikipedia.org/wiki/Anexo:Municipios_de_Espa%C3%B1a_por_poblaci%C3%B3n
    public static final String[] CITIES = {"Madrid", "Barcelona", "Valencia", "Sevilla", "Zaragoza", "Málaga", "Murcia",
            "Palma de Mallorca", "Bilbao", "Alicante", "Córdoba", "Valladolid", "Vigo", "Gijón", "Vitoria", "Granada",
            "Elche", "Oviedo", "Cartagena", "Móstoles", "Pamplona", "Almería", "Leganés", "San Sebastián", "Burgos",
            "Albacete", "Santander", "Castellón de la Plana", "Alcorcón", "Logroño", "Badajoz", "Huelva", "Salamanca",
            "Marbella", "Tarragona", "León", "Cádiz", "Alcobendas", "Jaén", "Baracaldo", "Gerona", "Santiago de Compostela",
            "Cáceres", "San Fernando", "Lorca", "El Puerto de Santa María", "Melilla", "Ceuta", "Guadalajara", "Toledo",
            "Pontevedra", "Torrevieja", "Torrente", "Arona", "Áviles", "Orihuela", "Rubí", "Valdemoro",
            "Ciudad Real", "Gandía", "Paterna", "Torremolinos", "Molina de Segura", "Benidorm", "Sagunto", "Irún", "Zamora",
            "Mérida", "Aranjuez", "Linares", "Ávila", "Cuenca", "Huesca", "Elda", "Segovia", "Pinto", "Villareal", "Ibiza",
            "Puertollano", "Portugalete", "Tres Cantos", "Mislata", "Lucena", "Puerto Real", "Denia", "Alcantarilla",
            "Basauri", "Plasencia", "Manacor", "Soria", "La Rinconada", "Puerto del Rosario", "Don Benito", "Tomelloso",
            "Miranda de Ebro", "Teruel", "Tudela", "Águilas", "Burriana", "Torre Pacheco", "Petrel", "Ronda", "Villena",
            "Tortosa", "Aranda de Duero", "Galapagar", "San Javier", "Santa Pola", "Inca", "Manises", "Coria del Río",
            "Ingenio", "Valdepeñas", "San Roque", "Puerto de la Cruz", "Durango", "Rota", "Ciudadela", "Carmona", "Lepe",
            "Lebrija", "Camas", "Candelaria", "Pineda de Mar", "Oliva", "Villarrobledo", "Onda", "Almansa", "Martos",
            "Los Barrios", "Almonte", "Barbate", "Laguna del Duero", "Vilaseca", "Altea", "Moncada", "Isla Cristina",
            "Cabra", "Baza", "Rosas", "Lora del Río", "Pozoblanco", "Zafra", "Mula", "Alcañiz", "La Solana", "Vera",
            "Tarancón", "Villaviciosa", "Alpedrete", "Cambados", "Santa Úrsula", "Pilas", "Canet del Mar", "Campo de Criptana",
            "La Carlota", "Tomiño", "Meco", "Sopelana", "Nules", "Sarria", "Gines", "Torrijos", "Abarán", "Godella", "Jaca",
            "Coria", "La Puebla", "Órdenes", "Guillena", "Salobreña", "Villamartín", "Valdés", "Valdemorillo", "Lena",
            "Peligros", "Santoña", "Fuensalida", "Alcora", "La Escala", "Brunete", "Caudete", "Bargas", "Villarrubia de los Ojos",
            "Campos", "Puentes de García", "Rodriguez", "Las Palmas de Gran Canaria", "Fuenlabrada", "Getafe", "Parla",
            "Las Rozas de Madrid", "Chiclana de la Frontera", "Talavera de la Reina", "Coslada", "Mijas"};
}
