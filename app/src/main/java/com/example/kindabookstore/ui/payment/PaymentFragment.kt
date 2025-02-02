package com.example.kindabookstore.ui.payment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.kindabookstore.R
import com.example.kindabookstore.common.Constants.CARD_NUMBER_APPLE_PAY
import com.example.kindabookstore.common.Constants.CARD_NUMBER_GOOGLE_PAY
import com.example.kindabookstore.common.Constants.CARD_NUMBER_MASTERCARD
import com.example.kindabookstore.common.Constants.CARD_NUMBER_PAYPAL
import com.example.kindabookstore.common.showSnackbar
import com.example.kindabookstore.data.model.Book
import com.example.kindabookstore.databinding.FragmentPaymentBinding
import java.text.NumberFormat
import java.util.Locale

class PaymentFragment : Fragment() {

    private var _binding: FragmentPaymentBinding? = null
    private val binding get() = _binding!!

    private val booksBasket = listOf(
        Book(1, "book", "sada", "sasha", 1.0, "asdadsa")
    ) // Замените это на реальные данные

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPaymentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        var totalPrice = 0f
        for (i in booksBasket) {
            totalPrice += i.price.toFloat()
        }
        binding.totalPriceText.text =
            NumberFormat.getCurrencyInstance(Locale("tr", "TR")).format(totalPrice)

        with(binding) {

            masterCard.checked()
            paypalCard.checked()
            appleCard.checked()
            googleCard.checked()

            editImage.setOnClickListener {
                if (editText.text == getString(R.string.edit)) {
                    addressTextInput.visibility = View.VISIBLE
                    editText.text = getString(R.string.save)
                    addressEditText.setText(addressText.text)
                } else {
                    addressTextInput.visibility = View.GONE
                    editText.text = getString(R.string.edit)
                    addressText.text = addressEditText.text.toString()
                    addressEditText.setText("")
                }
            }

            orderNowButton.setOnClickListener {
                if (masterCard.isChecked || paypalCard.isChecked || appleCard.isChecked || googleCard.isChecked) {
                    if (addressText.text.isEmpty().not()) {
                        showSuccessDialog()
                        // clearBasket() // Очистить корзину
                    } else {
                        it.showSnackbar(getString(R.string.address_error))
                    }
                } else {
                    it.showSnackbar(getString(R.string.order_now_error))
                }
            }

            cancelPaymentButton.setOnClickListener {
                findNavController().navigate(R.id.action_paymentFragment_to_navigation_cart)
            }
        }
    }

    private fun RadioButton.checked() {
        setOnClickListener {
            with(binding) {
                when (it.id) {
                    R.id.masterCard -> {
                        checkedRadioButton(
                            paypalCard, appleCard, googleCard,
                            CARD_NUMBER_MASTERCARD,
                            R.string.credit_card,
                            R.drawable.ic_mastercard
                        )
                    }
                    R.id.paypalCard -> {
                        checkedRadioButton(
                            masterCard, appleCard, googleCard,
                            CARD_NUMBER_PAYPAL,
                            R.string.paypal,
                            R.drawable.ic_paypal
                        )
                    }
                    R.id.appleCard -> {
                        checkedRadioButton(
                            masterCard, paypalCard, googleCard,
                            CARD_NUMBER_APPLE_PAY,
                            R.string.apple_pay,
                            R.drawable.ic_applepay
                        )
                    }
                    R.id.googleCard -> {
                        checkedRadioButton(
                            masterCard, appleCard, paypalCard,
                            CARD_NUMBER_GOOGLE_PAY,
                            R.string.google_pay,
                            R.drawable.ic_googlepay
                        )
                    }
                }
            }
        }
    }

    private fun checkedRadioButton(
        radioButton1: RadioButton,
        radioButton2: RadioButton,
        radioButton3: RadioButton,
        cardNumber: String,
        paymentMethod: Int,
        paymentTypeImage: Int,
    ) {
        radioButton1.isChecked = false
        radioButton2.isChecked = false
        radioButton3.isChecked = false
        binding.cardNumberText.text = cardNumber
        binding.paymentMethodText.text = getString(paymentMethod)
        binding.paymentTypeImage.setImageResource(paymentTypeImage)
    }

    private fun showSuccessDialog() {
        val layoutView = LayoutInflater.from(requireContext())
            .inflate(R.layout.success_order, null, false)
        val dialogBuilder = AlertDialog.Builder(requireContext())
        dialogBuilder.setView(layoutView)
        val alertDialog = dialogBuilder.create()

        alertDialog.show()
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

        Handler(Looper.getMainLooper()).postDelayed({
            alertDialog.dismiss()
            findNavController().navigate(R.id.navigation_home)
        }, 2000)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
