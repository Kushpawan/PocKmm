import SwiftUI
import Shared

class HomeViewModelWrapper: ObservableObject {
    let viewModel: HomeViewModel
    @Published var items: [HomeItem] = []

    init() {
        self.viewModel = HomeViewModel()
        self.items = viewModel.homeState.value as? [HomeItem] ?? []
    }

    func update(name: String, email: String, dob: String) {
        viewModel.updateList(name: name, email: email, dob: dob)
        self.items = viewModel.homeState.value as? [HomeItem] ?? []
    }
}

struct ContentView: View {
    @StateObject private var wrapper = HomeViewModelWrapper()
    @State private var name: String = ""
    @State private var email: String = ""
    @State private var birthDate = Date()
    @State private var showDatePicker = false

    private var dobString: String {
        let formatter = DateFormatter()
        formatter.dateFormat = "dd/MM/yyyy"
        return formatter.string(from: birthDate)
    }

    var body: some View {
        VStack(spacing: 16) {
            VStack(spacing: 12) {
                TextField("Enter Name", text: $name)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                
                TextField("Enter email ID", text: $email)
                    .textFieldStyle(RoundedBorderTextFieldStyle())
                
                // DOB Selector
                HStack {
                    Text("DOB: \(dobString)")
                    Spacer()
                    Button("Select Date") {
                        showDatePicker.toggle()
                    }
                }
                .padding(10)
                .background(RoundedRectangle(cornerRadius: 5).stroke(Color.gray.opacity(0.5)))
                
                if showDatePicker {
                    DatePicker("", selection: $birthDate, displayedComponents: .date)
                        .datePickerStyle(WheelDatePickerStyle())
                        .labelsHidden()
                }
                
                Button(action: {
                    wrapper.update(name: name, email: email, dob: dobString)
                    name = ""
                    email = ""
                    showDatePicker = false
                }) {
                    Text("Update List")
                        .frame(maxWidth: .infinity)
                        .padding()
                        .background(Color.blue)
                        .foregroundColor(.white)
                        .cornerRadius(8)
                }
            }
            .padding()

            ScrollView {
                VStack(spacing: 8) {
                    ForEach(wrapper.items, id: \.self) { item in
                        VStack(alignment: .leading, spacing: 4) {
                            HStack {
                                Text(item.name)
                                Spacer()
                                Text(item.email)
                            }
                            Text("DOB: \(item.dob)")
                                .font(.caption)
                                .foregroundColor(.secondary)
                        }
                        .padding()
                        .background(Color(UIColor.lightGray).opacity(0.3))
                        .cornerRadius(8)
                    }
                }
                .padding(.horizontal)
            }
        }
        .padding(.top, 32)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
