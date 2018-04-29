(ns spider-man-spec.core
  (:require [clojure.spec.alpha :as s]))

;; The data of the Spider-Man and Vulture profiles
(def spider-man-profile
  {::name "Spider-Man"
   ::real-name "Peter Benjamin Parker"
   ::identity ::secret
   ::affiliations
   {::current-affiliations #{"Avengers"}
    ::former-affiliations #{"Secret Defenders" "New Fantastic Four" "The Outlaws"}}
   ::power-grid {::durability 3
                 ::energy 4
                 ::fighting 5
                 ::intelligence 4
                 ::speed 5
                 ::strength 4}})

(def vulture-profile
  {::name "Vulture"
   ::real-name "Adrian Toomes"
   ::identity ::publicly-known
   ::affiliations
   {::current-affiliations {}
    ::former-affiliations #{"Sinister Twelve" "Sinister Six"}}
   ::power-grid {::durability 4
                 ::energy 3
                 ::fighting 4
                 ::intelligence 4
                 ::speed 5
                 ::strength 3}})

(def spider-man-characters [spider-man-profile vulture-profile])

;; The registered specs
(s/def ::name string?)

(s/def ::real-name string?)

(s/def ::identity #{::secret
                    ::publicly-known})

(s/def ::current-affilications (s/coll-of string? :kind set?))

(s/def ::former-affilications (s/coll-of string? :kind set?))

(s/def ::affiliations (s/keys :req [::current-affiliations] :opt [::former-affiliations]) )

(s/def ::power-value (s/and pos-int? #(>= % 1) #(<= % 7)))

(s/def ::durability ::power-value )
(s/def ::energy ::power-value )
(s/def ::fighting ::power-value )
(s/def ::intelligence ::power-value )
(s/def ::speed ::power-value )
(s/def ::strength ::power-value )

(s/def ::power-grid (s/keys :req [::durability ::energy ::fighting ::intelligence ::speed ::strength] ) )

(s/def ::profile (s/keys :req [::name ::real-name ::identity ::affiliations ::power-grid] ) )

(defn is-avenger? [profile]
  (contains? (::current-affiliations (::affiliations profile) ) "Avengers")
  )

(s/def ::avenger-profile (s/and ::profile is-avenger? ) )
