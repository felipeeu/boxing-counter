(ns boxing-alerts.core
  (:require
   [dynne.sampled-sound :refer [read-sound play]]))


(def boxing-bell-sound (read-sound "./resources/assets/sounds/boxing-bell.wav"))
(def knock-sound (read-sound "./resources/assets/sounds/knock.mp3"))


(def round-options '({:id "1" :value 3 :text "3 rounds of 3 minutes"}
                     {:id "2" :value 4 :text "4 rounds of 2 minutes"}
                     {:id "3" :value 12 :text "12 rounds of 3 minutes"}))

(defn seconds-to-min-sec [total-seconds]
  (let [minutes (quot total-seconds 60)
        seconds (rem total-seconds 60)]
    (format "%02d:%02d" minutes seconds)))

(defn countdown [moment-type round-number time-in-seconds]
  (doseq [i (range time-in-seconds 0 -1)]
    (println (str moment-type " " round-number  "- " (seconds-to-min-sec i)))
    (if (= i 10) (play knock-sound) nil)
    (Thread/sleep 1000)))

(defn play-round
  [current-round round-time]
  (play boxing-bell-sound)
  (println (str "Starting Round " current-round))
  (countdown "Round" current-round round-time)
  (println (str "End of Round " current-round))
  (play boxing-bell-sound))

(defn start-fight
  [prompted-rounds]
  (let [{:keys [value]} prompted-rounds
        one-minute 60
        two-minutes 120
        three-minutes 180
        number-of-rounds  value
        round-time (if (= value 4) two-minutes three-minutes)
        break-time one-minute]
    (loop [round-number number-of-rounds]
      (let [current-round (+ (- number-of-rounds round-number) 1)]
        (when (> round-number 0)
          (play-round current-round round-time)
          (when (> round-number 1)
            (countdown "Break" current-round break-time))
          (recur (dec round-number)))))))

(defn menu [{:keys [prompt options]}]

  (let [valid-options (set (map :id options))]
    (loop []
      (when prompt
        (println)
        (println prompt)
        (println))
      (doseq [{:keys [id  text]} options]
        (println (str " [" id "]") text))
      (println)
      (println "or press <enter> to cancel")

      (let [in  (read-line)]
        (cond (= in nil)
              :cancelled

              (not (valid-options in))
              (do
                (println (format "\n-- Invalid option '%s'!" in))
                (recur))

              :else
              (start-fight (first (filter #(= in (:id %)) options))))))))



(defn -main
  "The entry-point for lein run"
  []
  (menu {:prompt "How many rounds ?" :options round-options}))


