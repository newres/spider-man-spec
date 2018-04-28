(ns spider-man-spec.core-test
  (:require [clojure.test :refer :all]
            [spider-man-spec.core :refer :all]
            [clojure.spec.alpha :as s]
            ))

(deftest string-valid-test
  (testing "You can use an existing function with one parameter that returns true as a spec to check for validity."
    (is (s/valid? string? "Spider-Man"))))

(deftest string-conform-test
  (testing "You can use the spec for conforming which returns the conformed value."
    (is (= "Spider-Man" (s/conform string? "Spider-Man")))))

(deftest string-explain-data-test
  (testing "If a value is not valid you can ask for an explanation as a map of why it is not valid."
    (is (not (s/valid? string? 6))
        (= {:val 6 :predicate :clojure.spec.alpha/unknown} (s/explain-data string? 6))
        )))

(deftest name-valid-test
  (testing "You can use a registered spec as any other."
    (is (s/valid? :spider-man-spec.core/name "Spider-Man"))))

(deftest real-name-explain-data-test
  (testing "Once a spec is registered explanation can give pin point to the given predicate name, for which the value failed the spec.")
  (is (not (s/valid? :spider-man-spec.core/real-name 6))
      (= {:val 6 :predicate :spider-man-spec.core/real-name} (s/explain-data :spider-man-spec.core/real-name 6))
      ))

(deftest identity-conform-test
  (testing "You can use a set of values as a spec to check for conform as well."
    (is (= :spider-man-spec.core/secret (s/conform :spider-man-spec.core/identity :spider-man-spec.core/secret)))))

(deftest affiliation-valid-test
  (testing "You can use specs for maps to check for the validity of affiliations."
    (let [spider-man-affiliations (:spider-man-profile/affiliations spider-man-profile)]
      (is (s/valid? :spider-man-spec.core/identity :spider-man-spec.core/secret)))))

(deftest power-value-valid-test
  (testing "You can create specs from combined specs to express the limits of a power value."
      (is (s/valid? :spider-man-spec.core/power-value 4))))

(deftest power-value-valid-limits-test
  (testing "A power value should allow 1 and 7 as the minimum and maximum."
    (is (s/valid? :spider-man-spec.core/power-value 1))
    (is (s/valid? :spider-man-spec.core/power-value 7))
    ))

(deftest power-value-invalid-test
  (testing "A power value should allow of 0 and under and 8 or above are now allowed."
    (is (not  (s/valid? :spider-man-spec.core/power-value 0)))
    (is (not (s/valid? :spider-man-spec.core/power-value 8)))
    ))

(deftest power-value-invalid-floating-point-test
  (testing "A power value should not be a floating point value."
    (is (not  (s/valid? :spider-man-spec.core/power-value 1.4)))
    (is (not (s/valid? :spider-man-spec.core/power-value 4.5)))
    ))

(deftest is-avenger?-test
  (testing "Whether we can check if a character profile indicates if the character is an Avenger.")
  (is (is-avenger? spider-man-profile)))

(deftest is-avenger?-not-valid-test
  (testing "Checking if vulture is not accepted as an Avenger.")
  (is (not (is-avenger? vulture-profile))))

(deftest spider-man-is-an-avenger-test
  (testing "Checking if Spider-Man fulfills the avenger-profile spec.")
  (is (s/valid? :spider-man-spec.core/avenger-profile spider-man-profile )))

(deftest vulture-is-not-an-avenger-test
  (testing "Checking if Vulture does not fulfill the avenger-profile spec.")
  (is (not (s/valid? :spider-man-spec.core/avenger-profile vulture-profile ))))

;; (deftest spider-man-name-test
;;   (testing "Spider-Man's profile should have the right name."
;;     (is (= (:spider-man-spec.core/name spider-man-profile) "Spider-Man"))))

;; (deftest spider-man-real-name-test
;;   (testing "Spider-Man's profile should have the right real name."
;;     (is (= (:spider-man-spec.core/real-name spider-man-profile) "Peter Benjamin Parker"))))
