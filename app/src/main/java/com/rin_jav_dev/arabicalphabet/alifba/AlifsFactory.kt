package com.rin_jav_dev.arabicalphabet.alifba

import com.rin_jav_dev.arabicalphabet.R
import com.rin_jav_dev.arabicalphabet.database.alifs.Alif
import java.util.*


private    var alifs:ArrayList<Alif>?=null
private var testAlifs: ArrayList<Alif> = ArrayList();
private var filteredAlifs: ArrayList<Alif> = ArrayList();
val testAlifsLength=3
class AlifsFactory {
    companion object{
        @JvmStatic
        fun getAlifs():ArrayList<Alif>?{
                alifs = ArrayList()
                alifs!!.add(Alif(1,"ا","ʾalif",true,"ا","ـا","ـا",R.raw.alif))
                alifs!!.add(Alif(2,"ب","bāʾ",true,"بـ","ـبـ","ـب",R.raw.ba))
                alifs!!.add(Alif(3,"ت","tāʾ",true,"تـ","ـتـ","ـت",R.raw.ta))
                alifs!!.add(Alif(4,"ث","thāʾ",true,"ثـ","ـثـ","ـث",R.raw.tha))
                alifs!!.add(Alif(5,"ج","jīm",false,"جـ","ـجـ","ـج",R.raw.jiim))
                alifs!!.add(Alif(6,"ح","ḥāʾ",false,"حـ","ـحـ","ـح",R.raw.hha))
                alifs!!.add(Alif(7,"خ","khāʾ",false,"خـ","ـخـ","ـخ",R.raw.kha))
                alifs!!.add(Alif(8,"د","dāl",false,"د","ـد","ـد",R.raw.daal))
                alifs!!.add(Alif(9,"ذ","dhāl",false,"ذ","ـذ","ـذ",R.raw.thaal))
                alifs!!.add(Alif(10,"ر","rāʾ",false,"ر","ـر","ـر",R.raw.ra))
                alifs!!.add(Alif(11,"ز","zāy",false,"ز","ـز","ـز",R.raw.zay))
                alifs!!.add(Alif(12,"س","sīn",false,"سـ","ـسـ","ـس",R.raw.siin))
                alifs!!.add(Alif(13,"ش","shīn",false,"شـ","ـشـ","ـش",R.raw.shiin))
                alifs!!.add(Alif(14,"ص","ṣād",false,"صـ","ـصـ","ـص",R.raw.saad))
                alifs!!.add(Alif(15,"ض","ḍād",false,"ضـ","ـضـ","ـض",R.raw.daad))
                alifs!!.add(Alif(16,"ﻁ","ṭāʾ",false,"طـ","ـطـ","ـط",R.raw.thaa))
                alifs!!.add(Alif(17,"ظ","ẓāʾ",false,"ظـ","ـظـ","ـظ",R.raw.thaa))
                alifs!!.add(Alif(18,"ع","ʿayn",false,"عـ","ـعـ","ـع",R.raw.ayn))
                alifs!!.add(Alif(19,"غ","ghayn",false,"غـ","ـغـ","ـغ",R.raw.ghayn))
                alifs!!.add(Alif(20,"ﻑ","fāʾ",false,"فـ","ـفـ","ـف",R.raw.fa))
                alifs!!.add(Alif(21,"ﻕ","qāf",false,"قـ","ـقـ","ـق",R.raw.qaf))
                alifs!!.add(Alif(22,"ك","kāf",false,"كـ","ـكـ","ـك",R.raw.kaf))
                alifs!!.add(Alif(23,"ل","lām",false,"لـ","ـلـ","ـل",R.raw.lam))
                alifs!!.add(Alif(24,"م","mīm",false,"مـ","ـمـ","ـم",R.raw.miim))
                alifs!!.add(Alif(25,"ن","nūn",false,"نـ","ـنـ","ـن",R.raw.nuun))
                alifs!!.add(Alif(26,"ه","hāʾ",false,"هـ","ـهـ","ـه",R.raw.ha))
                alifs!!.add(Alif(27,"و","wāw",false,"و","ـو","ـو",R.raw.waw))
                alifs!!.add(Alif(28,"ﻱ","yāʾ",false,"يـ","ـيـ","ـي",R.raw.ya))
            return alifs;
        }

        @JvmStatic
        fun doFilterTestAlifs(): Int {
         //  filteredAlifs.clear()
         //
         //   for(alif in alifs!!){
         //    if(alif.enableForAlpfabetTest)
         //        filteredAlifs.add(alif)
         //
         //   }

            return 5
        }
        @JvmStatic
        fun getTestAlifs(): ArrayList<Alif> {
            testAlifs.clear()
            Collections.shuffle(filteredAlifs)
            for(i in 0..testAlifsLength){
                println(i)
                testAlifs.add(filteredAlifs.get(i))
            }
            return testAlifs
        }
    }





}