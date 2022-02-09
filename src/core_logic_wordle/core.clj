(ns core-logic-wordle.core
  (:require [clojure.core.logic :as l]))


(defn lettero
  "x is a letter, A-Z."
  [x]
  (l/membero x (map char (range (int \A) (inc (int \Z))))))

(l/defne nonmembero
  "x is a letter and x is not in the word"
  [x word]
  ;; x is not in an empty collection
  ([_ []])
  ([_ [first . rest]]
   (l/!= x first)
   (nonmembero x rest)))


(defn solve
  []
  (map #(apply str %)
       (l/run* [q]
               ;; the a-e variables are letters 1,2,3,4,5 of the word
               (l/fresh [a b c d e]
                        (lettero a)
                        (lettero b)
                        (lettero c)
                        (lettero d)
                        (lettero e)

               ;; what do we know so far:

               ;; we know that some letters are not in the word
               (nonmembero \A [a b c d e])
               (nonmembero \I [a b c d e])
               (nonmembero \E [a b c d e])
               (nonmembero \L [a b c d e])
               (nonmembero \D [a b c d e])
               (nonmembero \N [a b c d e])
               (nonmembero \T [a b c d e])
               (nonmembero \P [a b c d e])
               (nonmembero \Y [a b c d e])
               (nonmembero \G [a b c d e])
               
               ;; we know that M is in the word, but not in the second position
               (l/membero \R [a b c d e])
               (l/!= \R b)
               
               ;; O is there, but not in the second position
               (l/membero \O [a b c d e])
               (l/!= \O b)
               
               ;; U is there, but not in the fourth position
               (l/membero \U [a b c d e])
               (l/!= \U d)
               ;; We know that some of these are not in the right position
               (l/membero \M [a b c d e])
               (l/!= \M d)

               ;; The letters form a word
               (l/== q [a b c d e])))))

(comment

  ;; The search space is quite large, so you will have to wait a moment

  (solve)
  ; => ("BMORU" "BMROU" "BMUOR" "BMURO" "BUMOR" "BUMRO" "BUORM" "BUROM" "CMORU" "CMROU" "CMUOR" "CMURO" "CUMOR" "CUMRO" "CUORM" "CUROM" "FMORU" "FMROU" "FMUOR" "FMURO" "FUMOR" "FUMRO" "FUORM" "FUROM" "HMORU" "HMROU" "HMUOR" "HMURO" "HUMOR" "HUMRO" "HUORM" "HUROM" "JMORU" "JMROU" "JMUOR" "JMURO" "JUMOR" "JUMRO" "JUORM" "JUROM" "MBORU" "MBROU" "MBUOR" "MBURO" "KMORU" "KMROU" "KMUOR" "KMURO" "MCORU" "MCROU" "MCUOR" "MCURO" "KUMOR" "KUMRO" "KUORM" "KUROM" "MFORU" "MFROU" "MFUOR" "MFURO" "MHORU" "MHROU" "MHUOR" "MHURO" "MJORU" "MJROU" "MJUOR" "MJURO" "OBMRU" "OBURM" "MKORU" "MKROU" "MKUOR" "MKURO" "MMORU" "MMORU" "MMROU" "MMROU" "MMUOR" "MMUOR" "MMURO" "MMURO" "OCMRU" "OCURM" "MQORU" "MQROU" "MQUOR" "MQURO" "MSORU" "MSROU" "MSUOR" "MSURO" "MUBOR" "MUBRO" "MUCOR" "MUCRO" "MUFOR" "MUFRO" "MUHOR" "MUHRO" "MUJOR" "MUJRO" "MUKOR" "MUKRO" "MUMOR" "MUMOR" "MUMRO" "MUMRO" "MUOBR" "MUOCR" "MUOFR" "MUOHR" "MUOJR" "MUOKR" "MUOOR" "MUOOR" "MUOQR" "MUORB" "MUORC" "MUORF" "MUORH" "MUORJ" "MUORK" "MUORM" "MUORM" "MUORO" "MUORO" "MUORQ" "MUORR" "MUORR" "MUORS" "MUORU" "MUORU" "MUORV" "MUORW" "MUORX" "MUORZ" "MUOSR" "MUOVR" "MUOWR" "MUOXR" "MUOZR" "MURBO" "MURCO" "MUQOR" "MUQRO" "MURFO" "MURHO" "MURJO" "MURKO" "MUROB" "MUROC" "MUROF" "MUROH" "MUROJ" "MUROK" "MUROM" "MUROM" "MUROO" "MUROO" "MUROQ" "MUROR" "MUROR" "MUROS" "MUROU" "MUROU" "MUROV" "MUROW" "MUROX" "MUROZ" "MURQO" "MURRO" "MURRO" "MURSO" "MURVO" "MURWO" "MURXO" "MURZO" "MUSOR" "MUSRO" "MUUOR" "MUUOR" "MUURO" "MUURO" "MUVOR" "MUVRO" "MUWOR" "MUWRO" "MUXOR" "MUXRO" "MUZOR" "MUZRO" "MVORU" "MVROU" "MVUOR" "MVURO" "MWORU" "MWROU" "MWUOR" "MWURO" "MXORU" "MXROU" "MXUOR" "MXURO" "MZORU" "MZROU" "MZUOR" "MZURO" "OFMRU" "OFURM" "OHMRU" "OHURM" "OJMRU" "OJURM" "OKMRU" "OKURM" "OMBRU" "OMCRU" "OMFRU" "OMHRU" "OMJRU" "OMKRU" "OMMRU" "OMMRU" "OMORU" "OMORU" "OMRBU" "OMRCU" "OMQRU" "OMRFU" "OMRHU" "OMRJU" "OMRKU" "OMROU" "OMROU" "OMRQU" "OMRRU" "OMRRU" "OMRSU" "OMRVU" "OMRWU" "OMRXU" "OMRZU" "OMSRU" "OMUBR" "OMUCR" "OMUFR" "OMUHR" "OMUJR" "OMUKR" "OMUOR" "OMUOR" "OMUQR" "OMURB" "OMURC" "OMURF" "OMURH" "OMURJ" "OMURK" "OMURM" "OMURM" "OMURO" "OMURO" "OMURQ" "OMURR" "OMURR" "OMURS" "OMURU" "OMURU" "OMURV" "OMURW" "OMURX" "OMURZ" "OMUSR" "OMUVR" "OMUWR" "OMUXR" "OMUZR" "OMVRU" "OMWRU" "OMXRU" "OMZRU" "OQMRU" "OQURM" "OSMRU" "OSURM" "OUBRM" "OUCRM" "OUFRM" "OUHRM" "OUJRM" "OUKRM" "OUMBR" "OUMCR" "OUMFR" "OUMHR" "OUMJR" "OUMKR" "OUMOR" "OUMOR" "OUMRB" "OUMRC" "OUMQR" "OUMRF" "OUMRH" "OUMRJ" "OUMRK" "OUMRM" "OUMRM" "OUMRO" "OUMRO" "OUMRQ" "OUMRR" "OUMRR" "OUMRS" "OUMRU" "OUMRU" "OUMRV" "OUMRW" "OUMRX" "OUMRZ" "OUMSR" "OUMVR" "OUMWR" "OUMXR" "OUMZR" "OUORM" "OUORM" "OURBM" "OURCM" "OUQRM" "OURFM" "OURHM" "OURJM" "OURKM" "OUROM" "OUROM" "OURQM" "OURRM" "OURRM" "OURSM" "OURVM" "OURWM" "OURXM" "OURZM" "OUSRM" "OUURM" "OUURM" "OUVRM" "OUWRM" "OUXRM" "OUZRM" "OVMRU" "OVURM" "RBMOU" "RBUOM" "OWMRU" "OWURM" "OXMRU" "OXURM" "OZMRU" "OZURM" "RCMOU" "RCUOM" "RFMOU" "RFUOM" "QMORU" "QMROU" "QMUOR" "QMURO" "RHMOU" "RHUOM" "RJMOU" "RJUOM" "QUMOR" "QUMRO" "QUORM" "QUROM" "RKMOU" "RKUOM" "RMBOU" "RMCOU" "RMFOU" "RMHOU" "RMJOU" "RMKOU" "RMMOU" "RMMOU" "RMOBU" "RMOCU" "RMOFU" "RMOHU" "RMOJU" "RMOKU" "RMOOU" "RMOOU" "RMOQU" "RMORU" "RMORU" "RMOSU" "RMOVU" "RMOWU" "RMOXU" "RMOZU" "RMQOU" "RMROU" "RMROU" "RMSOU" "RMUBO" "RMUCO" "RMUFO" "RMUHO" "RMUJO" "RMUKO" "RMUOB" "RMUOC" "RMUOF" "RMUOH" "RMUOJ" "RMUOK" "RMUOM" "RMUOM" "RMUOO" "RMUOO" "RMUOQ" "RMUOR" "RMUOR" "RMUOS" "RMUOU" "RMUOU" "RMUOV" "RMUOW" "RMUOX" "RMUOZ" "RMUQO" "RMURO" "RMURO" "RMUSO" "RMUVO" "RMUWO" "RMUXO" "RMUZO" "RMVOU" "RMWOU" "RMXOU" "RMZOU" "RQMOU" "RQUOM" "RSMOU" "RSUOM" "RUBOM" "RUCOM" "RUFOM" "RUHOM" "RUJOM" "RUKOM" "RUMBO" "RUMCO" "RUMFO" "RUMHO" "RUMJO" "RUMKO" "RUMOB" "RUMOC" "RUMOF" "RUMOH" "RUMOJ" "RUMOK" "RUMOM" "RUMOM" "RUMOO" "RUMOO" "RUMOQ" "RUMOR" "RUMOR" "RUMOS" "RUMOU" "RUMOU" "RUMOV" "RUMOW" "RUMOX" "RUMOZ" "RUMQO" "RUMRO" "RUMRO" "RUMSO" "RUMVO" "RUMWO" "RUMXO" "RUMZO" "RUOBM" "RUOCM" "RUOFM" "RUOHM" "RUOJM" "RUOKM" "RUOOM" "RUOOM" "RUOQM" "RUORM" "RUORM" "RUOSM" "RUOVM" "RUOWM" "RUOXM" "RUOZM" "RUQOM" "RUROM" "RUROM" "RUSOM" "RUUOM" "RUUOM" "RUVOM" "RUWOM" "RUXOM" "RUZOM" "RVMOU" "RVUOM" "UBMOR" "UBMRO" "UBORM" "UBROM" "RWMOU" "RWUOM" "RXMOU" "RXUOM" "RZMOU" "RZUOM" "SMORU" "SMROU" "SMUOR" "SMURO" "UCMOR" "UCMRO" "UCORM" "UCROM" "SUMOR" "SUMRO" "SUORM" "SUROM" "UFMOR" "UFMRO" "UFORM" "UFROM" "UHMOR" "UHMRO" "UHORM" "UHROM" "UJMOR" "UJMRO" "UJORM" "UJROM" "UKMOR" "UKMRO" "UKORM" "UKROM" "UMBOR" "UMBRO" "UMCOR" "UMCRO" "UMFOR" "UMFRO" "UMHOR" "UMHRO" "UMJOR" "UMJRO" "UMKOR" "UMKRO" "UMMOR" "UMMOR" "UMMRO" "UMMRO" "UMOBR" "UMOCR" "UMOFR" "UMOHR" "UMOJR" "UMOKR" "UMOOR" "UMOOR" "UMOQR" "UMORB" "UMORC" "UMORF" "UMORH" "UMORJ" "UMORK" "UMORM" "UMORM" "UMORO" "UMORO" "UMORQ" "UMORR" "UMORR" "UMORS" "UMORU" "UMORU" "UMORV" "UMORW" "UMORX" "UMORZ" "UMOSR" "UMOVR" "UMOWR" "UMOXR" "UMOZR" "UMRBO" "UMRCO" "UMQOR" "UMQRO" "UMRFO" "UMRHO" "UMRJO" "UMRKO" "UMROB" "UMROC" "UMROF" "UMROH" "UMROJ" "UMROK" "UMROM" "UMROM" "UMROO" "UMROO" "UMROQ" "UMROR" "UMROR" "UMROS" "UMROU" "UMROU" "UMROV" "UMROW" "UMROX" "UMROZ" "UMRQO" "UMRRO" "UMRRO" "UMRSO" "UMRVO" "UMRWO" "UMRXO" "UMRZO" "UMSOR" "UMSRO" "UMUOR" "UMUOR" "UMURO" "UMURO" "UMVOR" "UMVRO" "UMWOR" "UMWRO" "UMXOR" "UMXRO" "UMZOR" "UMZRO" "UQMOR" "UQMRO" "UQORM" "UQROM" "USMOR" "USMRO" "USORM" "USROM" "UUMOR" "UUMOR" "UUMRO" "UUMRO" "UUORM" "UUORM" "UUROM" "UUROM" "UVMOR" "UVMRO" "UVORM" "UVROM" "UWMOR" "UWMRO" "UWORM" "UWROM" "UXMOR" "UXMRO" "UXORM" "UXROM" "UZMOR" "UZMRO" "UZORM" "UZROM" "VMORU" "VMROU" "VMUOR" "VMURO" "VUMOR" "VUMRO" "VUORM" "VUROM" "WMORU" "WMROU" "WMUOR" "WMURO" "WUMOR" "WUMRO" "WUORM" "WUROM" "XMORU" "XMROU" "XMUOR" "XMURO" "XUMOR" "XUMRO" "XUORM" "XUROM" "ZMORU" "ZMROU" "ZMUOR" "ZMURO" "ZUMOR" "ZUMRO" "ZUORM" "ZUROM")


  )
  
