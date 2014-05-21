(ns jump.entity)

; entity = {:id :player :traits [... {:type trait, :k v, ...} ...]}
(defn select-trait
  [trait entity]
  (let [traits (:traits entity)
        by-type #(= (:type %) trait)]
    (first (filter by-type traits))))
