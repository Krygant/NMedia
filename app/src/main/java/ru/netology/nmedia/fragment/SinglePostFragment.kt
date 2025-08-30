package ru.netology.nmedia.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import ru.netology.nmedia.adapter.OnInteractionListener
import ru.netology.nmedia.adapter.PostViewHolder
import ru.netology.nmedia.databinding.FragmentSinglePostBinding
import ru.netology.nmedia.dto.Post
import ru.netology.nmedia.util.idArg
import ru.netology.nmedia.viewModel.PostViewModel

class SinglePostFragment : Fragment() {

    companion object {
        var Bundle.idArg: Long? by idArg
    }

    private val viewModel: PostViewModel by viewModels(ownerProducer = ::requireParentFragment)


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val binding = FragmentSinglePostBinding.inflate(inflater, container, false)
        PostViewHolder(binding.postView, object : OnInteractionListener {
            override fun onRemove(post: Post) {
                super.onRemove(post)
            }

            override fun onEdit(post: Post) {
                super.onEdit(post)
            }
        })

        val postId = arguments?.idArg ?: -1
        viewModel.data.observe(viewLifecycleOwner) { posts ->
            val post = posts.find { it.id == postId } ?: return@observe
//            with(binding.postView) {
//                content = post.content
//            }
        }
        return binding.root
    }
}