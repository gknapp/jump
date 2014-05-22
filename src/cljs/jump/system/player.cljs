(ns jump.system.player)

(enable-console-print!)

; taken from
; https://github.com/google/closure-library/blob/master/closure/goog/events/keynames.js
(def keyname
  {27 :esc
   32 :space
   37 :left
   38 :up
   39 :right
   65 :a
   68 :d
   83 :s
   87 :w})

(def command
  {:esc   :pause
   :space :jump
   :up    :jump
   :w     :jump
   :left  :left
   :a     :left
   :right :right
   :d     :right})

(def input-cmd (atom nil))

(defn clear-cmd []
  (reset! input-cmd nil))

(defn input->command
  [input]
  (when-let [cmd (-> (.-keyCode input) keyname command)]
    (println "command" cmd)
    cmd))

(defn input->atom
  [atom input]
  (when-let [cmd (input->command input)]
    (reset! atom cmd)))

(defn bind-controls []
  (println "Bound controls")
  (set! (.-onkeydown js/document)
        #(input->atom input-cmd %)))
