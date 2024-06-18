package com.example.kindabookstore.ui.onboarding

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.kindabookstore.ui.onboarding.content.FirstFragment
import com.example.kindabookstore.ui.onboarding.content.SecondFragment
import com.example.kindabookstore.ui.onboarding.content.ThirdFragment

class OnboardingAdapter(fm: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fm, lifecycle) {

    private val content = listOf<Fragment>(
        FirstFragment(),
        SecondFragment(),
        ThirdFragment()
    )

    override fun getItemCount(): Int {
        return content.size
    }

    override fun createFragment(position: Int): Fragment {
        return content[position]
    }

}