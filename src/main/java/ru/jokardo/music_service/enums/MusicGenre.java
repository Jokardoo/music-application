package ru.jokardo.music_service.dto;

public enum MusicGenre {

    // ─── Классика и академическая ───
    CLASSICAL,          // симфония, опера, барокко, камерная, хоровая

    // ─── Джаз и блюз ───
    JAZZ,               // bebop, swing, fusion, smooth jazz
    BLUES,              // delta, chicago, rhythm-and-blues

    // ─── Рок ───
    ROCK,               // classic, hard, soft, prog, psychedelic, garage
    ALTERNATIVE_ROCK,   // indie, post-punk, grunge, shoegaze, emo
    PUNK,               // punk rock, hardcore, pop-punk, skate punk
    METAL,              // heavy, thrash, death, black, doom, power, nu

    // ─── Поп ───
    POP,                // synth-pop, electropop, dance-pop, teen pop
    INDIE_POP,          // dream pop, chamber pop, indie pop
    K_POP,              // k-pop, j-pop (азиатская поп-сцена)
    LATIN_POP,          // latin pop, reggaeton

    // ─── Хип-хоп ───
    HIP_HOP,            // rap, trap, drill, boom bap, grime, phonk

    // ─── Электронная музыка ───
    HOUSE,              // deep, progressive, tech house, afro house
    TECHNO,             // minimal, industrial techno
    TRANCE,             // psy-trance, progressive trance
    AMBIENT,            // chillout, downtempo, new age, drone
    DUBSTEP,            // dubstep, future bass, bass music
    DRUM_AND_BASS,      // dnb, jungle, breakbeat
    SYNTHWAVE,          // synthwave, vaporwave, electro
    DISCO_FUNK,         // disco, nu-disco, funk, electro-funk

    // ─── R&B и соул ───
    RNB_SOUL,           // rnb, neo-soul, motown, quiet storm

    // ─── Регги и карибская музыка ───
    REGGAE,             // reggae, dancehall, dub, ska, rocksteady
    LATIN,              // salsa, bachata, cumbia, merengue, bossa nova

    // ─── Фолк и этника ───
    FOLK,               // country, bluegrass, americana, celtic, world
    AFRO,               // afrobeat, afro-pop, highlife, kizomba
    ASIAN_TRADITIONAL,  // city pop, c-pop, enka, bollywood, mandopop

    // ─── Религиозная и вокальная ───
    GOSPEL,             // gospel, spiritual, worship, gregorian chant

    // ─── Саундтреки и атмосфера ───
    SOUNDTRACK,         // score, video game music, epic, musical

    // ─── Прочее ───
    INSTRUMENTAL,       // acoustic, a cappella, spoken word, poetry
    OTHER               // всё, что не попало в категории
}