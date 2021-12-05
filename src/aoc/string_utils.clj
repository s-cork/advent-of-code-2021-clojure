(ns aoc.string-utils
  (:refer-clojure :exclude [ints])
  (:require [clojure.string :as str]))

(defn str->int
  ([s] (Integer/parseInt s))
  ([s base] (Integer/parseInt s base)))

(defn bin-str->int [s]
  (Integer/parseInt s 2))

(defn split-white-space [s]
  (str/split (str/trim s) #"\s+"))

(defn split-multi-lines [s]
  (str/split (str/trim s) #"\n{2,}"))

(defn split-comma [s]
  (str/split s #","))

(defn str->ints
  "assumes the str has some sort of deliminator"
  [s]
  (map read-string (re-seq #"-?\d+" s)))
