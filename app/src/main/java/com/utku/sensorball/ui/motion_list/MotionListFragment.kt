package com.utku.sensorball.ui.motion_list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.base.ui.BaseFragment
import com.data.data.entities.SensorRecord
import com.data.data.util.Result
import com.utku.sensorball.databinding.FragmentMotionListBinding
import com.utku.sensorball.ui.root.RootViewModel
import org.koin.androidx.viewmodel.ext.android.sharedViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MotionListFragment : BaseFragment<FragmentMotionListBinding>({
    FragmentMotionListBinding.inflate(it)
}) {

    private val sensorRecordList: MutableList<SensorRecord> = mutableListOf()

    override val viewModel: RootViewModel by sharedViewModel()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        onMotionAdd()
        setRecyclerView()
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        fetchRecords()
    }

    private fun fetchRecords() {
        lifecycleScope.launchWhenResumed {
            viewModel.getAllRecord().observe(viewLifecycleOwner) {
                if (it is Result.Success) {
                    viewBinding.motionRecordRecyclerView.adapter?.apply {
                        sensorRecordList.clear()
                        sensorRecordList.addAll(it.data ?: mutableListOf())
                        notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun setRecyclerView() {
        viewBinding.motionRecordRecyclerView.apply {
            adapter = MotionListAdapter(sensorRecordList) {
                playRecord(it)
            }
            layoutManager = LinearLayoutManager(context).apply {
                orientation = LinearLayoutManager.VERTICAL
            }
        }
    }

    private fun playRecord(sensorRecord: SensorRecord) {
        viewModel.selectedRecord.value = sensorRecord
        findNavController().navigate(
            MotionListFragmentDirections.actionMotionListFragmentToMotionRecordFragment(
                true
            )
        )
    }

    private fun onMotionAdd() {
        viewBinding.motionAddButton.setOnClickListener {
            findNavController().navigate(
                MotionListFragmentDirections.actionMotionListFragmentToMotionRecordFragment(
                    false
                )
            )
        }
    }
}