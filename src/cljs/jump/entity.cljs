(ns jump.entity)

; could be a macro
(defn entity
  "create entity, merging all attribute maps"
  [identity & attrs]
  {:id (keyword identity)
   :attrs (reduce merge {} attrs)})

(defn deep-merge
  "Recursively merges maps. If keys are not maps, the last value wins."
  [& vals]
  (if (every? map? vals)
    (apply merge-with deep-merge vals)
    (last vals)))

(defn update
  "Merge attribute changes into entity map"
  [entity changes]
  (deep-merge entity {:attrs changes}))

(defn root
  [path]
  (let [path (if (vector? path) path [path])]
    (vec (cons :attrs path))))

; entity = {:id identity :attrs {:attr {:k v}, :attr {:k v}, ...}}
(defn attr
  ([entity path]
     (get-in entity (root path)))
  ([entity path value]
     (assoc-in entity (root path) value)))

(defn has-attr
  [entity path]
  (not (nil? (attr entity path))))
