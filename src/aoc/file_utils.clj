(ns aoc.file-utils
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [aoc.string-utils :refer [str->ints split-multi-lines]]))

(defn read-file [path]
  (slurp (io/resource path)))

(defn read-lines [path]
  (str/split-lines (read-file path)))

(defn read-ints [path]
  "assumes ints"
  (str->ints (read-file path)))

(defn read-multi-lines [path]
  "splits on more than one line"
  (split-multi-lines (read-file path)))

