package id.indocyber.moviedbassigment.fragment.video

import android.app.AlertDialog
import android.content.DialogInterface
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.options.IFramePlayerOptions
import com.pierfrancescosoffritti.androidyoutubeplayer.core.ui.DefaultPlayerUiController
import dagger.hilt.android.AndroidEntryPoint
import id.indocyber.common.AppResponse
import id.indocyber.common.base.BaseFragment
import id.indocyber.moviedbassigment.R
import id.indocyber.moviedbassigment.databinding.VideoFragmentLayoutBinding
import id.indocyber.moviedbassigment.view_model.VideoViewModel

@AndroidEntryPoint
class VideoFragment : BaseFragment<VideoViewModel, VideoFragmentLayoutBinding>() {
    override val vm: VideoViewModel by viewModels()
    override val layoutResourceId: Int = R.layout.video_fragment_layout
    private val args by navArgs<VideoFragmentArgs>()

    override fun initBinding(binding: VideoFragmentLayoutBinding) {
        super.initBinding(binding)
        binding.posterPath = args.posterPath
        vm.videoData.observe(this) {
            when (it) {
                is AppResponse.AppResponseSuccess -> {
                    Log.i("AppResponse", "Succes cuy")
                    val listener = object : AbstractYouTubePlayerListener() {
                        override fun onReady(youTubePlayer: YouTubePlayer) {
                            super.onReady(youTubePlayer)
                            val videoId = it.data.key
                            youTubePlayer.cueVideo(videoId, 0f)

                            val defaultPlayerUiController =
                                DefaultPlayerUiController(binding.video, youTubePlayer)
                            binding.video.setCustomPlayerUi(defaultPlayerUiController.rootView)
                        }
                    }

                    val option = IFramePlayerOptions.Builder().controls(0).build()
                    binding.video.initialize(listener, option)

                    binding.loadingBar.visibility = View.GONE
                    binding.retryButton.visibility = View.GONE
                    binding.video.visibility = View.VISIBLE
                    binding.image.visibility = View.VISIBLE
                }
                is AppResponse.AppResponseLoading -> {
                    binding.loadingBar.visibility = View.VISIBLE
                    binding.retryButton.visibility = View.GONE
                    binding.video.visibility = View.GONE
                    binding.image.visibility = View.VISIBLE
                }
                is AppResponse.AppResponseError -> {
                    if (it.e?.message.orEmpty().startsWith("Data")) {
                        AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage(it.e?.message)
                            .setPositiveButton("Kembali") { _, _ ->
                                vm.popBackStack()
                            }
                            .setNegativeButton("Tutup") { dialog, _ ->
                                dialog.cancel()
                            }
                            .create().show()
                    } else if (it.e?.message.orEmpty().startsWith("Request")) {
                        AlertDialog.Builder(context)
                            .setTitle("Error")
                            .setMessage(it.e?.message)
                            .setPositiveButton("Kembali") { _, _ ->
                                vm.popBackStack()
                            }
                            .setNegativeButton("Tutup") { dialog, _ ->
                                dialog.cancel()
                            }
                            .create().show()
                    }
                    binding.loadingBar.visibility = View.GONE
                    binding.retryButton.visibility = View.VISIBLE
                    binding.video.visibility = View.GONE
                    binding.image.visibility = View.VISIBLE
                }
            }

        }
        vm.loadData(args.movieId)
        binding.retryButton.setOnClickListener {
            vm.loadData(args.movieId)
        }
    }
}