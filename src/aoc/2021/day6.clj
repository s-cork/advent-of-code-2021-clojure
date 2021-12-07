(ns aoc.2021.day6
  (:require [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]))

;;(def init-fishes (str->ints "3,4,3,1,2"))
(def init-fishes (file-utils/read-ints "2021/day6.txt"))

(defn map-births [freqs]
  (map #(get freqs (mod % 7) 0) (range 9)))

(defn accum-sum [curr nxt]
  (conj curr (+ nxt (last curr))))

(defn get-first-nine [lantern-fish]
  (->> lantern-fish
       frequencies
       map-births
       (reduce accum-sum [(count lantern-fish)])
       rest))

(def first-nine (get-first-nine init-fishes))

(def lazy-fish-babies
  (lazy-cat first-nine
            (map + (drop 2 lazy-fish-babies) lazy-fish-babies)))

(comment
  (nth lazy-fish-babies 79)
  (nth lazy-fish-babies 255)
  ;
  )

;; alternative lazy seq idea
(defn fib9 [nine]
  (let [nxt (+ (first nine) (nth nine 2))
        next-nine (conj (vec (rest nine)) nxt)]
    (lazy-seq (cons nxt (fib9 next-nine)))))

(defn get-lazy-fish-babies [init-fishes]
  (let [first-nine (get-first-nine init-fishes)]
    (lazy-cat first-nine (fib9 first-nine))))