(ns aoc.2020.day2
  (:require [clojure.string :as str]
            [aoc.string-utils :refer [str->int]]
            [aoc.file-utils :as file-utils]
            [aoc.debug-utils :refer [-log->]]))

(def input (str/split-lines "1-3 a: abcde\n1-3 b: cdefg\n2-9 c: ccccccccc"))
(def input (file-utils/read-lines "2020/day2.txt"))

(def matcher #"(\d+)-(\d+) ([a-z]): ([a-z]+)")

(defn parse-line [line]
  (let [[_ min max letter password] (re-find matcher line)]
    (zipmap [:min :max :letter :password]
            [(str->int min) (str->int max) letter password])))

(defn parse-lines [lines]
  (map parse-line lines))

(defn valid-1? [{:keys [min max letter password]}]
  (let [occurrences (count (re-seq (re-pattern letter) password))]
    (<= min occurrences max)))

(defn in-pos? [pos letter password]
  (= (str (get password (dec pos))) letter))

(defn valid-2? [{:keys [min max letter password]}]
  (let [in-min (in-pos? min letter password)
        in-max (in-pos? max letter password)]
    (and (or in-min in-max) (not (and in-min in-max)))))

(defn solve [pred]
  (->> input
       parse-lines
       (filter pred)
       count))

(comment
  (solve valid-1?)
  (solve valid-2?)
  ;
  )