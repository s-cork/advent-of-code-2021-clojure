(ns aoc.debug-utils
  (:require [clojure.pprint :refer [pprint]]))

(defn logging [f]
  (fn [& args]
    (pprint args)
    (apply f args)))

(defn -log-> [res]
  (pprint res)
  res)

(comment
  (->> [1 2 3 4]
       -log->
       (map inc)
       -log->)
  ((logging map) inc [1 2 3 4])
  ;;
  )