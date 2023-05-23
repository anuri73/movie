![](web/img/poster.avif)
*(Photo
by <a href="https://unsplash.com/@tysonmoultrie?utm_source=unsplash&utm_medium=referral&utm_content=creditCopyText">
Roberto Contreras</a> on <a href="https://unsplash.com/photos/n7A4TjAQLN4">Unsplash</a>)*

# A movie recommendation system built using Scala, Spark and Hadoop

Recommendation system is a widely used machine learning technique that has many applications in E-commerce (Amazon,
Alibaba), video streaming (Netflix, Disney+), social network (Facebook, Linkedin) and many other areas. Because of the
large amount of data in those services, nowadays most of industry-level recommendation systems are built in big data
frameworks like Spark and Hadoop. So in this repository I want to make a movie recommendation system using Scala, Spark
and Hadoop.

## 1. Introduction to recommendation system

### 1.1 Different recommendataion system algorithms

Recommendataion system algorithms can be categorized into two main types: content-based recommendataion and
collaborative filtering. Below is a summary table describing their differences.

| |Content-based recommendataion | Collaborative filtering |
|--|-----------|--------------|
|Description|Utilizes product characteristics to recommend similar products to what a user previously liked.|Predicts the interest of a user by collecting preference information from many other users.|
|Assumption|If person P1 and person P2 have the same opinion on product D1, then P1 is more likely to have the same opinion on product D2 with P2 than with a random chosen person Px.|If person P likes product D1 which has a collection of attributes, he/she is more likely to like product D2 which shares those attributes than product D3 which doesn't.|
|Example|news/article recommendataion|movie recommendataion, Amazon product recommendation|
|Advantages| - The model doesn't need any user data input, so easier to scale.<br /> - Capable of catching niche items with feature engineering.| - No domain knowledge needed, highly transferrable model.<br /> - Capable of helping users discover new interests.|
|Disadvantages| - Requires domain knowledge.<br /> - Limited ability to expand user's interests.| - Cold-start problem: need to work with existing data, can't handle fresh items/users.<br /> - Difficulty in expanding features for items.|

### 1.2 Collaborative filtering and Spark ALS

In this post, we will use collaborative filtering as the recommendation algorithm. How collaborative filtering works is
this: First, we consider the ratings of all users to all items as a matrix, and this matrix can be factorized to two
separate matrices, one being a user matrix where rows represent users and columns are latent factors; the other being a
item matrix where rows are latent factors and columns represent items (see figure below). During this factorization
process, the missing values in the ratings matrix can be filled, which serve as predictions of user ratings to items,
and then we can use them to give recommendations to users.

<p align="center">
<img src="web/img/matrix-factorization.png">
<br>
<em> Matrix factorization in collaborative filtering</em></p>

[ALS (alternating least squares)](https://towardsdatascience.com/prototyping-a-recommender-system-step-by-step-part-2-alternating-least-square-als-matrix-4a76c58714a1)
is a mathematically optimized implementation of collaborative filtering that uses Alternating Least Squares (ALS) with
Weighted-Lamda-Regularization (ALS-WR) to find optimal factor weights that minimize the least squares between predicted
and actual ratings. [Spark's MLLib package](https://spark.apache.org/docs/latest/ml-guide.html) has
a [built-in ALS function](https://spark.apache.org/docs/latest/mllib-collaborative-filtering.html), and we will use it
in this post.

## 2. System setup

* Macos 13.2.1 (22D68)
* JDK 1.8.0_275
* Scala 2.12.10
* Spark 3.1.2
* Hadoop 3.2.2
* IntelliJ IDEA (2021.3)

For detailed setup of system prerequisites,
follow [haocai1992 post](https://haocai1992.github.io/data/science/2022/01/11/how-to-set-up-your-environment-for-spark.html)
.

## 3. Dataset

In this project we will use the [MovieLens dataset](https://grouplens.org/datasets/movielens/) from University of
Minnesota, Twin Cities. You can download **ml-100k** (4.7M) by running:

```
wget https://files.grouplens.org/datasets/movielens/ml-100k.zip
```

Unzip the zip file by running:

```
unzip ml-100k.zip
```

We mainly use two data files:

* `u.data`: user ratings data, includes **user id**, **item id**, **rating**, **timestamp**.
* `u.item`: movies data, includes **item id**, **movie title**, **release date**, **imdb url**, etc.

## 4. Runnning in Spark

### 4.1 Clone code from Github

Before running in Spark, clone code from my [Github Repository](https://github.com/anuri73/movie.git) to your local
directory using:

```
git clone https://github.com/anuri73/movie.git
```

Open the folder in IntelliJ IDEA.

### 4.2 Make up docker containers

Before we start, we need to build docker images and make up containers.

```bash
make install
```

This command is composition of a few bash commands:

- Create and copy environment variable to .env file
- Create docker volumes
- docker-compose up --build --remove-orphans
- docker exec namenode hdfs dfs -mkdir -p /data/ml-100k
- docker exec namenode hdfs dfs -put /data/hadoop/namenode/ml-100k /data

### 4.3 Train recommendataion model in Spark

For the convenience - there is already predefined bash command:

```bash
make train
```

This command will train our model as if user selected following movies:

- Toy Story (1995)
- Balto (1995)
- Pocahontas (1995)
- Aladdin (1992)
- Oliver & Company (1988)
- Copycat (1995)
- Fair Game (1995)

### 4.4 Generating recommendations in Spark

For the convenience - there is already predefined bash command:

```bash
make recommend
```

## 8. Summary

And there you go, we have built a recommendataion system using Scala + Spark + Hadoop (with Docker), Congratualations! I
hope you found this repository useful.

## Contact

* **Author**: Urmat Zhenaliev
* **Email**: [urmat.zhenaliev@gmail.com](mailto:urmat.zhenaliev@gmail.com)
* **Github**: [https://github.com/anuri73](https://github.com/anuri73)
* **Linkedin**: [https://www.linkedin.com/in/sonkei](https://www.linkedin.com/in/sonkei)