import { Component, OnInit, AfterViewInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormBuilder, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';

interface FaqItem {
  question: string;
  answer: string;
  isActive: boolean;
}

interface ContactInfo {
  icon: string;
  title: string;
  lines: string[];
}

@Component({
  selector: 'app-contact',
  standalone: true,
  imports: [CommonModule, ReactiveFormsModule],
  templateUrl: './contact.component.html',
  styleUrls: ['./contact.component.scss']
})
export class ContactComponent implements OnInit, AfterViewInit {
  contactForm!: FormGroup;
  formSubmitted = false;
  isSubmitting = false;
  mobileMenuOpen = false;

  // Contact information cards
  contactInfoCards: ContactInfo[] = [
    {
      icon: 'map-marker-alt',
      title: 'Nous rendre visite',
      lines: ['123 Avenue Cheikh Anta Diop', 'Dakar, Sénégal']
    },
    {
      icon: 'phone-alt',
      title: 'Nous appeler',
      lines: ['+221 33 456 7890', '+221 77 123 4567']
    },
    {
      icon: 'envelope',
      title: 'Nous écrire',
      lines: ['contact@lifelink.sn', 'support@lifelink.sn']
    },
    {
      icon: 'clock',
      title: 'Horaires d\'ouverture',
      lines: ['Lun - Ven: 8h - 18h', 'Sam: 9h - 15h']
    }
  ];

  // FAQ items
  faqItems: FaqItem[] = [
    {
      question: 'Qui peut donner son sang ?',
      answer: 'En général, toute personne âgée de 18 à 70 ans, pesant au moins 50 kg et en bonne santé peut donner son sang. Certaines conditions médicales ou voyages récents peuvent temporairement vous empêcher de donner.',
      isActive: false
    },
    {
      question: 'À quelle fréquence puis-je donner mon sang ?',
      answer: 'Les hommes peuvent donner leur sang jusqu\'à 6 fois par an, avec un intervalle minimum de 8 semaines entre chaque don. Les femmes peuvent donner jusqu\'à 4 fois par an, avec un intervalle minimum de 12 semaines.',
      isActive: false
    },
    {
      question: 'Le don de sang est-il douloureux ?',
      answer: 'La plupart des donneurs ne ressentent qu\'une légère piqûre lors de l\'insertion de l\'aiguille. Le processus de don est généralement indolore et ne dure que 8 à 10 minutes pour un don de sang total.',
      isActive: false
    },
    {
      question: 'Comment me préparer pour un don de sang ?',
      answer: 'Assurez-vous d\'être bien hydraté, d\'avoir mangé un repas sain, d\'avoir bien dormi la veille et d\'éviter l\'activité physique intense juste avant le don. Apportez une pièce d\'identité et prévoyez environ une heure pour l\'ensemble du processus.',
      isActive: false
    }
  ];

  constructor(private fb: FormBuilder) {}

  ngOnInit(): void {
    this.initForm();
  }

  ngAfterViewInit(): void {
    // Initialize animations on scroll
    this.initAnimations();
  }

  /**
   * Initialize the contact form with validation
   */
  initForm(): void {
    this.contactForm = this.fb.group({
      name: ['', [Validators.required, Validators.minLength(2)]],
      email: ['', [Validators.required, Validators.email]],
      phone: [''],
      subject: ['', Validators.required],
      message: ['', [Validators.required, Validators.minLength(10)]]
    });
  }

  /**
   * Toggle mobile menu visibility
   */
  toggleMobileMenu(): void {
    this.mobileMenuOpen = !this.mobileMenuOpen;
  }

  /**
   * Toggle FAQ item expansion
   * @param index Index of the FAQ item to toggle
   */
  toggleFaqItem(index: number): void {
    // Close all other FAQ items
    this.faqItems.forEach((item, i) => {
      if (i !== index) {
        item.isActive = false;
      }
    });
    
    // Toggle the selected FAQ item
    this.faqItems[index].isActive = !this.faqItems[index].isActive;
  }

  /**
   * Handle form submission
   */
  onSubmit(): void {
    if (this.contactForm.invalid) {
      // Mark all fields as touched to show validation errors
      Object.keys(this.contactForm.controls).forEach(key => {
        const control = this.contactForm.get(key);
        control?.markAsTouched();
      });
      return;
    }

    this.isSubmitting = true;

    // In a real application, you would call your API service here
    // This is just a simulation of the API call
    setTimeout(() => {
      this.formSubmitted = true;
      this.isSubmitting = false;
      
      // Log form data (for demonstration)
      console.log(this.contactForm.value);
      
      // Reset form for future use
      this.contactForm.reset();
    }, 1500);
  }

  /**
   * Initialize scroll animations
   */
  private initAnimations(): void {
    const animateElements = (): void => {
      const elements = document.querySelectorAll('.card, .contact-form, .map, .faq-item');
      
      elements.forEach(element => {
        const elementPosition = element.getBoundingClientRect().top;
        const windowHeight = window.innerHeight;
        
        if (elementPosition < windowHeight - 100) {
          element.classList.add('fade-in');
        }
      });
    };
    
    // Run on scroll and initial load
    window.addEventListener('scroll', animateElements);
    animateElements();
  }

  /**
   * Check if a form control has an error
   * @param controlName Name of the form control
   * @param errorName Name of the error to check
   * @returns Boolean indicating if the error exists
   */
  hasError(controlName: string, errorName: string): boolean {
    const control = this.contactForm.get(controlName);
    return control !== null && control.touched && control.hasError(errorName);
  }
}