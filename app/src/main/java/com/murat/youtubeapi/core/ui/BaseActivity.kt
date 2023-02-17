package com.murat.youtubeapi.core.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding

abstract class BaseActivity  <VB: ViewBinding, VM: BaseViewModel>: AppCompatActivity(){

    protected lateinit var binding: VB
    protected abstract fun inflateViewBinding(inflater: LayoutInflater) :VB
    protected lateinit var viewModel: VM



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= inflateViewBinding(layoutInflater)
        setContentView(binding.root)

        initViewModel()
        setupConnection()
        initViews()
        initListener()
    }

    open fun initViews(){}
    open fun initListener(){}
    open fun initViewModel(){}
    open fun setupConnection(){}

}