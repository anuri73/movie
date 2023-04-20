package urmat.jenaliev.source.movie

final case class Movie(
  movieId: Int,
  movieTitle: String,
  releaseDate: String,
  videoReleaseDate: String,
  IMDb: String,
  unknown: Boolean,
  Action: Boolean,
  Adventure: Boolean,
  Animation: Boolean,
  Children: Boolean,
  Comedy: Boolean,
  Crime: Boolean,
  Documentary: Boolean,
  Drama: Boolean,
  Fantasy: Boolean,
  FilmNoir: Boolean,
  Horror: Boolean,
  Musical: Boolean,
  Mystery: Boolean,
  SciFi: Boolean,
  Thriller: Boolean,
  War: Boolean,
  Western: Boolean
)
