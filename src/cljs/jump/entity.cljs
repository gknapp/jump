(ns jump.entity)

; could be a macro
(defn entity
  [identity & traits]
  {:id identity
   :traits (reduce merge {} traits)})

; entity = {:id identity :traits {:trait {:k v}, :trait {:k v}, ...}}
(defn trait
  [trait entity]
  (get-in entity [:traits trait]))
