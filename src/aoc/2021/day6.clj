(ns aoc.2021.day6
  (:require [aoc.string-utils :refer [str->ints]]
            [aoc.file-utils :as file-utils]))

(def init-fishes (str->ints "3,4,3,1,2"))
;;(def init-fishes (file-utils/read-ints "2021/day6.txt"))

(defn map-babies [freqs]
  (map #(get freqs (mod % 7) 0) (range 9)))

(defn get-first-nine [lantern-fish]
  (-> lantern-fish
      frequencies
      map-babies))

(def first-nine (get-first-nine init-fishes))

(def lazy-fish-babies
       (lazy-cat first-nine
                 (map + (drop 2 lazy-fish-babies) lazy-fish-babies)))

(defn get-nth-day [n]
  (->> (get-lazy-fish-babies init-fishes)
      (take n)
      (apply +)
      (+ (count init-fishes))))

(comment
  (get-nth-day 80)
  (get-nth-day 256)
  ;
  )

;; alternative lazy seq idea
(defn fib9 [nine]
  (let [nxt (+ (first nine) (nth nine 2))
        next-nine (conj (vec (rest nine)) nxt)]
    (lazy-seq (cons c (fib9 next-nine)))))

(defn get-lazy-fish-babies [init-fishes]
  (let [first-nine (get-first-nine init-fishes)]
    (lazy-cat first-nine (fib9 first-nine))))