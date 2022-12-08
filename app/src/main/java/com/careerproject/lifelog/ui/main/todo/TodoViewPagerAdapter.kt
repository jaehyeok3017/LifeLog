package com.careerproject.lifelog.ui.main.todo

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.careerproject.lifelog.ui.main.todo.future.NextFragment
import com.careerproject.lifelog.ui.main.todo.previous.CheckFragment

class TodoViewPagerAdapter(fragmentActivity: FragmentActivity) :
    FragmentStateAdapter(fragmentActivity) {
    private var fragments: ArrayList<Fragment> = ArrayList()

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        when(position){
            0 -> return CheckFragment()
            1 -> return NextFragment()
        }
        return CheckFragment()
    }

    fun addFragment(fragment: Fragment) {
        fragments.add(fragment)
        notifyItemInserted(fragments.size - 1)
    }

    fun removeFragment() {
        fragments.removeLast()
        notifyItemRemoved(fragments.size)
    }
}